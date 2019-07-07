package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import prv.pgergely.cts.common.commsystem.HttpCommSystem;
import prv.pgergely.ctscountry.domain.SwaggerFeed;
import prv.pgergely.ctscountry.domain.SwaggerFeed.Feeds;

@Service
public class FeedSource {
	
	private Gson gson;
	
	@Autowired
	private HttpCommSystem http;
	
	private FeedSource(){
		gson = new Gson();
	}
	
	public List<Feeds> getFeeds(String transitApiKey) throws IOException{
		List<Feeds> feedList = new ArrayList<>();
		String feeds = http.getRequest(String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&page=100&limit=100&type=gtfs", transitApiKey)); //Dummy query to know the number of pages.
		SwaggerFeed allFeed = gson.fromJson(removeArrayNoti(feeds), SwaggerFeed.class);
		for (int i = 1; i <= allFeed.results.numPages; i++) {// Because I need all GTFS type feed, but swagger supports querying by page only.
			String feedUrl = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&page=%d&limit=100&type=gtfs", transitApiKey, i);
			String pageFeeds = http.getRequest(feedUrl);
			SwaggerFeed actualPage = gson.fromJson(removeArrayNoti(pageFeeds), SwaggerFeed.class);
			List<Feeds> tempFeed =  Arrays.asList(actualPage.results.feeds);
			feedList.addAll(tempFeed);
		}
		
		return feedList;
	}
	
	public Feeds getFeeds(String transitApiKey, long feedId) throws IOException{
		String feedUrl = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&location=%d&limit=100&type=gtfs", transitApiKey, feedId);
		String specifiedFeed = http.getRequest(feedUrl);
		SwaggerFeed actualPage = gson.fromJson(removeArrayNoti(specifiedFeed), SwaggerFeed.class);
		
		return actualPage.results.feeds.length==0 ? new Feeds[]{}[0] : actualPage.results.feeds[0];
	}
	
	private String removeArrayNoti(String jsonFeed){
		return jsonFeed.replaceAll("\"u\":\\[\\]", "\"u\":\\{\\}");// Yeah, this a jury-rigged solution, because swagger sometimes(especially when no download link) change the {} brackets to [].
	}
}
