package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import prv.pgergely.ctscountry.configurations.TransitFeedsTemplate;
import prv.pgergely.ctscountry.domain.SwaggerFeed;
import prv.pgergely.ctscountry.domain.SwaggerFeed.Feeds;
import prv.pgergely.ctscountry.modules.TransitFeedResponse;

@Service
public class FeedSource {
	
	@Autowired
	private TransitFeedResponse transitResp;
	
	public List<Feeds> getFeeds() throws IOException{
		List<Feeds> feedList = new ArrayList<>();
		SwaggerFeed allFeed = transitResp.getFeed(100).getBody();
		for (int i = 1; i <= allFeed.results.numPages; i++) {// Because I need all GTFS type feed, but swagger supports querying by page only.
			SwaggerFeed actualPage = transitResp.getFeed(i).getBody();
			List<Feeds> tempFeed =  Arrays.asList(actualPage.results.feeds);
			feedList.addAll(tempFeed);
		}
		
		return feedList;
	}
	
	public Feeds getFeeds(long feedId) throws IOException{//
		SwaggerFeed actualPage = transitResp.getFeeds(feedId).getBody();
		return actualPage.results.feeds.length==0 ? new Feeds[]{}[0] : actualPage.results.feeds[0];
	}
}
