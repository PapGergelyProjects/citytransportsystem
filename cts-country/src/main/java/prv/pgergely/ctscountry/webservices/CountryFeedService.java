package prv.pgergely.ctscountry.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.services.FeedSource;

@RestController
@RequestMapping(path="/transit-feed")
public class CountryFeedService {

	@Autowired
	private FeedSource src;
	
	@GetMapping(path="/greet")
	public String greet() {
		return "Test Works";
	}
	
	@GetMapping(path="/getFeeds", produces = "application/json")
	public List<Feeds> getFeeds(){
		try {
			return src.getFeeds();
		} catch (IOException e) {
			return new ArrayList<Feeds>();
		}
	}
	
	@GetMapping(path="/getFeed/{feedId}", produces = "application/json")
	public Feeds getFeed(@PathVariable Long feedId) {
		try {
			return src.getFeed(feedId);
		} catch (Exception e) {
			return new ResponseEntity<Feeds>(HttpStatus.BAD_REQUEST).getBody();
		}
	}
}