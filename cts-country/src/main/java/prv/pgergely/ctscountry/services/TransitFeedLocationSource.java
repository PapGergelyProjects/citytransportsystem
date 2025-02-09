package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.transitfeed.FeedLocationsJson;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedLocationJson;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedLocationJson.Locations;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedLocationJson.Results;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
@Deprecated
public class TransitFeedLocationSource {
	
	@Autowired
	private TransitFeedApi feed;
	
	@Autowired
	private FeedSource feedSrc;
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	public List<FeedLocationsJson> getLocations(boolean onlyActive) throws IOException {
		List<FeedLocationsJson> locationList = new ArrayList<>();
		TransitFeedLocationJson location = feed.getLocations().getBody();
		Map<Long, FeedVersion> versionMap = feedVersion.getFeedVersions().stream().collect(Collectors.toMap(k -> k.getFeedId(), v -> v));
		List<Feeds> feeds = feedSrc.getFeeds();
		Locations[] locations = Optional.of(location.results).orElse(new Results()).locations;
		Set<Long> parentIds = Arrays.asList(locations).stream().map(m -> m.parentId).collect(Collectors.toSet());
		List<Locations> refinedLocations = Arrays.asList(locations).stream().filter(p -> {
			if(!parentIds.contains(p.id)) {
				if(onlyActive) {
					return versionMap.containsKey(p.id);
				}
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		for(Locations loc : refinedLocations) {
			Feeds actFeed = feeds.stream().filter(p -> (p.location.id == loc.id && p.feedUrl.urlDirectLink != null && p.latest != null)).findFirst().orElse(null);
			if(actFeed == null) {
				continue;
			}
			FeedVersion vers = Optional.ofNullable(versionMap.get(loc.id)).orElse(new FeedVersion());
			FeedLocationsJson json = new FeedLocationsJson();
			json.setId(loc.id);
			json.setTitle(loc.rawLocationName);
			json.setDsUrl(vers.getSourceUrl());
			json.setLat(loc.lat);
			json.setLon(loc.lng);
			json.setEnabled(versionMap.containsKey(json.getId()));
			json.setActive(vers.isActive());
			json.setSchemaName(vers.getSchemaName());
			json.setState(vers.getState());
			json.setFeedTitle(actFeed.feedTitle);
			LocalDateTime lastVers = Instant.ofEpochMilli(actFeed.latest.timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
			json.setLatestVersion(OffsetDateTime.of(lastVers, ZoneOffset.UTC));
			locationList.add(json);
		}
		
		return locationList;
	}
	
}
