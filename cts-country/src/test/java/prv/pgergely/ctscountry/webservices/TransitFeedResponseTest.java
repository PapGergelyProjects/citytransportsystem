package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.domain.TransitFeedJson;
import prv.pgergely.ctscountry.modules.TransitFeedApi;

@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransitFeedResponseTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TransitFeedApi feedResp;
	
	@Test
	@DisplayName("Get feed test")
	public void testGetFeed() {
		ResponseEntity<TransitFeedJson> transFeed = feedResp.getFeed(100);
		TransitFeedJson instance = transFeed.getBody();
		assertEquals(HttpStatus.OK.getReasonPhrase(), instance.status);
	}
}
