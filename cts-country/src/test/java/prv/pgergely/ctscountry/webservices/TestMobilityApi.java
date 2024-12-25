package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeeds;
import prv.pgergely.ctscountry.services.MobilityApiFeedService;

@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMobilityApi {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private MobilityApiFeedService srvc;
	
	@Test
	@DisplayName("Acquiring mdb-990 record from mobility api")
	public void test401() {
		MobilityGtfsFeeds feed = srvc.getGtfsFeed("mdb-990");
		assertNotNull(feed);
		final String feedId = feed.getId();
		assertEquals("mdb-990", feedId);
	}
	
}
