package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.domain.mobility.gtfs.Location;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.services.MobilityApi;

@Disabled("")
@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMobilityApi {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private MobilityApi srvc;
	
	@Test
	@Order(1)
	@DisplayName("Acquiring mdb-990 record")
	public void testOneFeed() {
		MobilityGtfsFeed feed = srvc.getGtfsFeed("mdb-990");
		assertNotNull(feed);
		final String feedId = feed.getId();
		assertEquals("mdb-990", feedId);
	}
	
	@Test
	@Order(2)
	@DisplayName("Filtering all gtfs feeds by country code")
	public void testAllFeeds() {
		List<MobilityGtfsFeed> feeds = srvc.getAllGtfsFeeds("HU");
		assertTrue(!feeds.isEmpty());
		MobilityGtfsFeed feed = feeds.get(0);
		assertTrue(!feed.getLocations().isEmpty());
		Location feedLoc = feed.getLocations().get(0);
		assertEquals("HU", feedLoc.getCountryCode());
	}
}
