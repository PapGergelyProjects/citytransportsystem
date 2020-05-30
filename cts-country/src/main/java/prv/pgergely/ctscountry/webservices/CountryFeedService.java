package prv.pgergely.ctscountry.webservices;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.ctscountry.domain.FeedLocationsJson;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.domain.docker.DockerContainer;
import prv.pgergely.ctscountry.services.DockerService;
import prv.pgergely.ctscountry.services.FeedSource;
import prv.pgergely.ctscountry.services.TransitFeedLocationSource;
import prv.pgergely.ctscountry.utils.docker.ContainerStatus;

@RestController
@RequestMapping(path="/transit-feed")
public class CountryFeedService {

	@Autowired
	private FeedSource src;
	
	@Autowired
	private TransitFeedLocationSource locationSrc;
	
	@Autowired
	private DockerService dockerSrvc;
	
	@GetMapping(path="/hello")
	public String greet() {
		return "Test Works";
	}
	
	@GetMapping(path="/getFeeds", produces = "application/json")
	public ResponseEntity<List<FeedLocationsJson>> getFeeds(){
		try {
			return new ResponseEntity<List<FeedLocationsJson>>(locationSrc.getLocations(), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<List<FeedLocationsJson>>(new ArrayList<FeedLocationsJson>(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path="/getFeed/{feedId}", produces = "application/json")
	public ResponseEntity<Feeds> getFeed(@PathVariable Long feedId) {
		try {
			return new ResponseEntity<Feeds>(src.getFeed(feedId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Feeds>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path="**/{urlPart}", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<DefaultResponse> getForAnyReqest(@PathVariable String urlPart) {
		DefaultResponse resp = new DefaultResponse();
		resp.message = "This service currently is not available.";
		resp.urlPart = urlPart;
		resp.statusCode = HttpStatus.FORBIDDEN.value();
		
		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.FORBIDDEN);
	}
	
	@GetMapping(path="/containers")
	public ResponseEntity<Map<ContainerStatus, List<DockerContainer>>> getContainers(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		headers.set("X-Docker", "OK");
		headers.set("Cts-Country", "true");
		
		return new ResponseEntity<Map<ContainerStatus, List<DockerContainer>>>(dockerSrvc.getContainers(), headers, HttpStatus.ACCEPTED);
	}
}
