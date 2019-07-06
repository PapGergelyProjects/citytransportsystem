package prv.pgergely.cts.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
	
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainPageServiceTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate template;
	
	@Test
	public void testApp() throws Exception {
		ResponseEntity<String> resp = template.getForEntity(new URI("http://localhost:"+port+"/hello/SpringTest"), String.class);
		assertEquals("Hello Servlet SpringTest", resp.getBody());
	}
}		