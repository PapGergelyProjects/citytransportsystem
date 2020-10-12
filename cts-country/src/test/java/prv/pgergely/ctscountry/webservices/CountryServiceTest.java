package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.domain.ResponseData;
import prv.pgergely.ctscountry.domain.SelectedFeed;
import prv.pgergely.ctscountry.interfaces.FeedVersionDao;
import prv.pgergely.ctscountry.model.FeedVersion;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountryServiceTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate temp;
	
	@Autowired
	private FeedVersionDao data;
	
	@Test
	@Order(1)
	@DisplayName("Insert new test record")
	public void testInsertVersion() {
		SelectedFeed feed = new SelectedFeed();
		feed.id = -42;
		feed.title = "Test feed";
		feed.latest = LocalDate.now();
		
		ResponseEntity<ResponseData> resp = temp.postForEntity("/register_feed", feed, ResponseData.class);
		assertEquals(201, resp.getBody().statusCode);
		assertEquals("Created", resp.getBody().message);
		
		FeedVersion version = data.getFeedVersionById(-42);
		assertEquals(-42, version.getFeedId());
		assertEquals("Test feed", version.getTitle());
	}
	
	@Test
	@Order(2)
	@DisplayName("Update test record")
	public void testUpdateVersion() {
		SelectedFeed vers = new SelectedFeed();
		vers.id = -42;
		vers.title = "Test feed updated";
		vers.latest = LocalDate.now();
		
		HttpHeaders head = new HttpHeaders();
		head.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SelectedFeed> entity = new HttpEntity<>(vers, head); 
		ResponseEntity<ResponseData> resp = temp.exchange("/update_feed", HttpMethod.PUT, entity, ResponseData.class);
		assertEquals(202, resp.getBody().statusCode);
		assertEquals("Accepted", resp.getBody().message);
		
		FeedVersion version = data.getFeedVersionById(-42);
		assertEquals(-42, version.getFeedId());
		assertEquals("Test feed updated", version.getTitle());
	}
	
	@Test
	@Order(3)
	@DisplayName("Delete test record")
	public void deleteVersion() {
		HttpHeaders head = new HttpHeaders();
		HttpEntity<Long> entity = new HttpEntity<>(new Long(-42), head);
		
		ResponseEntity<ResponseData> resp = temp.exchange("/delete_feed/-42", HttpMethod.DELETE, entity, ResponseData.class);
		assertEquals(204, resp.getStatusCodeValue());
		
		FeedVersion version = data.getFeedVersionById(-42);
		assertEquals(null, version);
	}
	
}
