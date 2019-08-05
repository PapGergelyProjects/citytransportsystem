package prv.pgergely.ctscountry.webservices;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.ctscountry.domain.ResponseData;
import prv.pgergely.ctscountry.domain.SelectedFeed;
import prv.pgergely.ctscountry.interfaces.FeedVersionService;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;
import prv.pgergely.ctscountry.model.FeedVersion;

@RestController
@RequestMapping(path="/")
public class CountryService {
	
	@Autowired
	private FeedVersionService feedVersion;
	
	@Autowired
	@Qualifier(TemplateQualifier.RESPONSE_ACCEPTED)
	private ResponseData response;
	
	@RequestMapping(path="/versions", method= {RequestMethod.GET,RequestMethod.HEAD})
	public ResponseEntity<List<FeedVersion>> getVersion() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		headers.set("X-Version", "1.0");
		
		return new ResponseEntity<List<FeedVersion>>(feedVersion.getFeedVersions(), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/version", method= {RequestMethod.GET,RequestMethod.HEAD})
	public ResponseEntity<String> getHead(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping(path="/register_feed", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ResponseData insertVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, true);
		feedVersion.insert(version);
		return new ResponseEntity<ResponseData>(response, HttpStatus.ACCEPTED).getBody();
	}
	
	@PutMapping(path="/update_feed", consumes = "application/json;charset=UTF-8")
	public void updateVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, false);
		feedVersion.update(version);
	}
	
	@DeleteMapping(path="/delete_feed/{feedId}")
	public ResponseData deleteVersion(@PathVariable long feedId) {
		FeedVersion version = new FeedVersion(feedId);
		feedVersion.deleteFeedVersion(version);
		return new ResponseEntity<ResponseData>(response, HttpStatus.ACCEPTED).getBody();
	}
}
