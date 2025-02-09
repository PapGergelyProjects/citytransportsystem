package prv.pgergely.ctscountry.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.mobility.BoundingCoordinates;
import prv.pgergely.cts.common.domain.mobility.GtfsFeedData;
import prv.pgergely.ctscountry.domain.mobility.feeds.ExternalIds;
import prv.pgergely.ctscountry.domain.mobility.gtfs.BoundingBox;
import prv.pgergely.ctscountry.domain.mobility.gtfs.LatestDataset;
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
		List<MobilityGtfsFeed> feeds = api.getAllGtfsFeeds(country).stream()
																	.filter(p -> p.getLatestData()!=null && p.getLatestData().getBoundinBox() != null)
																	.filter(p -> !"tld".equals(p.getExternalId().getFirst().getSource()))
																	.collect(Collectors.toList());
		Map<Long, FeedVersion> allRegisteredVersion = feedVersion.getFeedVersions().stream().collect(Collectors.toMap(k -> k.getFeedId(), v -> v));
		return feeds.stream().map(feed -> {
			final ExternalIds extIds = feed.getExternalId().getFirst();
			final Long externalId = Long.valueOf(extIds.getExternalId());
			final FeedVersion vers = Optional.ofNullable(allRegisteredVersion.get(externalId)).orElse(new FeedVersion());
			final LatestDataset dataSet = feed.getLatestData();
			final BoundingBox bb = dataSet.getBoundinBox();
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
			json.setLatestVersion(OffsetDateTime.parse(dataSet.getDownloadAt()));
			
			return json;
		}).collect(Collectors.toList());
	}
	
	private String refinedCountry(List<Location> locations) {
		return locations.stream().map(m -> m.getCountryCode()).collect(Collectors.joining(" | "));
	}
	
	private String refineLocationData(List<Location> locations) {
		return locations.stream().map(m -> {
			StringJoiner join = new StringJoiner(" ");
			join.add(m.getCountry());
			Optional.ofNullable(m.getSubDivName()).ifPresent(div -> join.add(div));
			Optional.ofNullable(m.getMunicipality()).ifPresent(mun -> join.add(mun));
			return join.toString();
		}).collect(Collectors.joining(" | "));
	}
}
