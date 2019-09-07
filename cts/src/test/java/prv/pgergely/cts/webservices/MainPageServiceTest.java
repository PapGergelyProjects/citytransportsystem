package prv.pgergely.cts.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import prv.pgergely.cts.domain.DefaultResponse;
	
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainPageServiceTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate template;
	
	@Test
	@DisplayName("Initial test")
	public void testApp() throws Exception {
		ResponseEntity<String> resp = template.getForEntity("/api/hello/SpringTest", String.class);
		assertEquals("Hello Servlet SpringTest", resp.getBody());
	}
	
	@Test
	@DisplayName("Test catch everything web service with undefined service.")
	public void testCatchEverything() throws Exception {
		ResponseEntity<DefaultResponse> resp = template.getForEntity("/api/items", DefaultResponse.class);
		assertEquals(403, resp.getBody().statusCode);
		assertEquals("items", resp.getBody().urlPart);
	}
}