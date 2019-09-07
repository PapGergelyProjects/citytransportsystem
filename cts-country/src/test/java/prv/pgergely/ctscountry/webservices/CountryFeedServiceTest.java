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

@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryFeedServiceTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate temp;
	
	@BeforeAll
	public static void setJson() {

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
		
	}
}
