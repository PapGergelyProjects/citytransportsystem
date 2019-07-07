package prv.pgergely.ctscountry.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.ctscountry.domain.SwaggerFeed.Feeds;
import prv.pgergely.ctscountry.services.FeedSource;

@RestController
@RequestMapping(path="/")
public class CountryService {
	
	@Value("${transit_feed_key}")
	private String transitApiKey;
	
	@Autowired
	private FeedSource src;
	
	@GetMapping(path="/get_feeds", produces = "application/json")
	public List<Feeds> getFeeds(){
		try {
			return src.getFeeds(transitApiKey);
		} catch (IOException e) {
			return new ArrayList<Feeds>();
		}
	}

}
