package prv.pgergely.ctscountry.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.mobility.BoundingCoordinates;
import prv.pgergely.cts.common.domain.mobility.GtfsFeedData;
import prv.pgergely.ctscountry.domain.mobility.feeds.ExternalIds;
import prv.pgergely.ctscountry.domain.mobility.gtfs.BoundingBox;
import prv.pgergely.ctscountry.domain.mobility.gtfs.Location;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class MobilityGtfsService {
	
	@Autowired
	private MobilityApi api;
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	public List<GtfsFeedData> getFeedsByCountry(String country) {
		List<MobilityGtfsFeed> feeds = api.getAllGtfsFeeds(country);
		Map<Long, FeedVersion> allRegisteredVersion = feedVersion.getFeedVersions().stream().collect(Collectors.toMap(k -> k.getFeedId(), v -> v));
		return feeds.stream().map(feed -> {
			final ExternalIds extIds = feed.getExternalId().getFirst();
			final Long externalId = Long.valueOf(extIds.getExternalId());
			final FeedVersion vers = allRegisteredVersion.get(externalId);
			final BoundingBox bb = feed.getLatestData().getBoundinBox();
			final List<Location> locs = feed.getLocations();
			GtfsFeedData json = new GtfsFeedData();
			json.setId(externalId);
			json.setTitle(feed.getProvider());
			json.setCountryCode(refinedCountry(locs));
			json.setDsUrl(vers.getSourceUrl());
			json.setBoundingCoord(new BoundingCoordinates(bb.getMinLat(), bb.getMinLng(), bb.getMaxLat(), bb.getMaxLng()));
			json.setEnabled(allRegisteredVersion.containsKey(json.getId()));
			json.setActive(vers.isActive());
			json.setSchemaName(vers.getSchemaName());
			json.setState(vers.getState());
			json.setFeedTitle(refineLocationData(locs));
			json.setLatestVersion(OffsetDateTime.parse(feed.getLatestData().getDownloadAt()));
			
			return json;
		}).collect(Collectors.toList());
	}
	
	private String refinedCountry(List<Location> locations) {
		return locations.stream().map(m -> m.getCountry()).collect(Collectors.joining(" | "));
	}
	
	private String refineLocationData(List<Location> locations) {
		return locations.stream().map(m -> m.getCountry()+" "+m.getSubDivName()).collect(Collectors.joining(" | "));
	}
}
