package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.domain.TransitFeedJson;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.modules.TransitFeedApi;

@Service
public class FeedSource {
	
	@Autowired
	private TransitFeedApi transitResp;
	
	public List<Feeds> getFeeds() throws IOException{
		List<Feeds> feedList = new ArrayList<>();
		TransitFeedJson allFeed = transitResp.getFeed(100).getBody();
		for (int i = 1; i <= allFeed.results.numPages; i++) {// Because I need all the GTFS type feed, but swagger supports querying by page only.
			TransitFeedJson actualPage = transitResp.getFeed(i).getBody();
			List<Feeds> tempFeed =  Arrays.asList(actualPage.results.feeds);
			feedList.addAll(tempFeed);
		}
		return feedList;
	}
	
	public Feeds getFeed(long feedId) throws IOException{
		TransitFeedJson actualPage = transitResp.getFeeds(feedId).getBody();
		return actualPage.results.feeds.length==0 ? new Feeds[]{}[0] : actualPage.results.feeds[0];
	}
}
