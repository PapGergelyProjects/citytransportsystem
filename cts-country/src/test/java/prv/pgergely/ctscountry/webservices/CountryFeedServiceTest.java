package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import prv.pgergely.ctscountry.ApplicationCountryComponents;
import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.domain.TransitFeedJson;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryFeedServiceTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate temp;
	
	private static Feeds testFeed;
	
	@BeforeAll
	public static void setJson() {
		testFeed = new Feeds();
		testFeed.id = "vermont-translines/566";
		testFeed.typeOfFeed = "gtfs";
		testFeed.feedTitle = "Vermont Translines GTFS";
		testFeed.location = new TransitFeedJson.Location();
		testFeed.location.id=415;
		testFeed.location.pid=35;
		testFeed.location.rawLocationTitle="Vermont, USA";
		testFeed.location.locationTitle="Vermont";
		testFeed.location.lat=44.558803;
		testFeed.location.lng=-72.577841;
		testFeed.feedUrl= new TransitFeedJson.FeedURL();
		testFeed.feedUrl.urlInfo="http://vermont-gtfs.org/";
		testFeed.feedUrl.urlDirectLink="http://data.trilliumtransit.com/gtfs/vttranslines-vt-us/vttranslines-vt-us.zip";
		testFeed.latest = new TransitFeedJson.Latest();
		testFeed.latest.timestamp=0;
	}
	
	@Test
	@DisplayName("Greet test")
	public void testGreet() {
		ResponseEntity<String> ent = temp.getForEntity("http://localhost:"+port+"/transit-feed/greet",String.class);
		assertEquals("Test Works", ent.getBody());
	}
	
	@Test
	@DisplayName("GetFeed test with id 415")
	public void testGetFeed() throws Exception {
		String url = "http://localhost:"+port+"/transit-feed/get_feed/"+415;
		ResponseEntity<Feeds> resp = temp.getForEntity(url, Feeds.class);
		Feeds feed = resp.getBody();
		feed.latest.timestamp=0;
		
		assertEquals(testFeed, feed);
	}
}
