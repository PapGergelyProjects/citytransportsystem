package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.domain.FeedLocationsJson;
import prv.pgergely.ctscountry.domain.FeedLocationsJson.Feed;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.domain.TransitFeedLocationJson;
import prv.pgergely.ctscountry.domain.TransitFeedLocationJson.Locations;
import prv.pgergely.ctscountry.domain.TransitFeedLocationJson.Results;
import prv.pgergely.ctscountry.modules.TransitFeedApi;

@Service
public class TransitFeedLocationSource {
	
	@Autowired
	private TransitFeedApi feed;
	
	@Autowired
	private FeedSource feedSrc;
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;

	private List<FeedLocationsJson> locationList = new ArrayList<>();
	
	public List<FeedLocationsJson> getLocations() throws IOException {
		TransitFeedLocationJson location = feed.getLocations().getBody();
		List<Long> versions = feedVersion.getFeedVersions().stream().map(m -> m.getFeedId()).collect(Collectors.toList());
		List<Feeds> feeds = feedSrc.getFeeds();
		Locations[] locations = Optional.of(location.results).orElse(new Results()).locations;
		Set<Long> parentIds = Arrays.asList(locations).stream().map(m -> m.parentId).collect(Collectors.toSet());
		List<Locations> refinedLocations = Arrays.asList(locations).stream().filter(p -> !parentIds.contains(p.id)).collect(Collectors.toList());
		for(Locations loc : refinedLocations) {
			FeedLocationsJson json = new FeedLocationsJson();
			json.id = loc.id;
			json.title = loc.rawLocationName;
			json.feed = new Feed();
			json.isEnabled = versions.contains(json.id);
			feeds.stream().filter(p -> (p.location.id == loc.id && p.feedUrl.urlDirectLink != null && p.latest != null)).forEach(e -> {
				json.feed.title = e.feedTitle;
				json.feed.latest = Instant.ofEpochMilli(e.latest.timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDate();
				locationList.add(json);
			});
		}
		
		return locationList;
	}
	
}
