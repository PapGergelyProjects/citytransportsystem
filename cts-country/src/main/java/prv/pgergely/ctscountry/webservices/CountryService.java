package prv.pgergely.ctscountry.webservices;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.domain.ResponseData;
import prv.pgergely.ctscountry.domain.SelectedFeed;
import prv.pgergely.ctscountry.model.FeedVersion;
import prv.pgergely.ctscountry.services.FeedOperations;
import prv.pgergely.ctscountry.services.FeedVersionServiceImpl;

@RestController
@RequestMapping(path="/")
public class CountryService {
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	@Autowired
	private FeedOperations operation;
	
	@RequestMapping(path="/versions", method= {RequestMethod.GET, RequestMethod.HEAD})
	public ResponseEntity<List<FeedVersion>> getVersion() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		headers.set("X-Version", "1.0");
		
		return new ResponseEntity<List<FeedVersion>>(feedVersion.getFeedVersions(), headers, HttpStatus.OK);
	}
	
	@PostMapping(path="/register_feed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseData> insertVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, true);
		operation.create(version);
		ResponseData data = new ResponseData();
		data.message = HttpStatus.CREATED.getReasonPhrase();
		data.statusCode = HttpStatus.CREATED.value();
		
		return new ResponseEntity<ResponseData>(data, HttpStatus.CREATED);
	}
	
	@PutMapping(path="/update_feed", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseData> updateVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, false);
		feedVersion.update(version);
		
		ResponseData data = new ResponseData();
		data.message = HttpStatus.ACCEPTED.getReasonPhrase();
		data.statusCode = HttpStatus.ACCEPTED.value();
		
		return new ResponseEntity<ResponseData>(data, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(path="/delete_feed/{feedId}")
	public ResponseEntity<Void> deleteVersion(@PathVariable long feedId) {
		try {
			FeedVersion version = new FeedVersion(feedId);
			operation.delete(version);
			
			return ResponseEntity.noContent().build();
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Void>(e.getStatusCode());
		}
		
	}
	
//	@RequestMapping(path="**/{urlPart}", method= {RequestMethod.GET, RequestMethod.POST})
//	public ResponseEntity<DefaultResponse> getForAnyReqest(@PathVariable String urlPart) {
//		DefaultResponse resp = new DefaultResponse();
//		resp.message = "This service currently is not available.";
//		resp.urlPart = urlPart;
//		resp.statusCode = HttpStatus.FORBIDDEN.value();
//		
//		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.FORBIDDEN);
//	}
}
