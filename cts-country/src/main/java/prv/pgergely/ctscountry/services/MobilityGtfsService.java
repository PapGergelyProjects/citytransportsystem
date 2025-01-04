package prv.pgergely.ctscountry.services;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.mobility.GtfsFeedData;
import prv.pgergely.cts.common.domain.transitfeed.FeedLocationsJson;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class MobilityGtfsService {
	
	@Autowired
	private MobilityApi api;
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	public List<FeedLocationsJson> getFeedsByCountry(String country) {
		List<MobilityGtfsFeed> feeds = api.getAllGtfsFeeds(country);
		List<FeedVersion> allVersion = feedVersion.getFeedVersions();
		feeds.stream().map(feed -> {
			GtfsFeedData json = new GtfsFeedData();
			json.setId(feed.getId());
			json.setTitle(feed.getProvider());
//			json.setDsUrl(vers.getSourceUrl());
//			json.setLat(loc.lat);
//			json.setLon(loc.lng);
//			json.setEnabled(versionMap.containsKey(json.getId()));
//			json.setActive(vers.isActive());
//			json.setSchemaName(vers.getSchemaName());
//			json.setState(vers.getState());
//			json.setFeedTitle(actFeed.feedTitle);
//			json.setLatestVersion(Instant.ofEpochMilli(actFeed.latest.timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDate());
			
			return json;
		});
		
		return Arrays.asList();
	}
}
