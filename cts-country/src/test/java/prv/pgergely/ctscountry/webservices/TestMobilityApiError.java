package prv.pgergely.ctscountry.webservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.services.MobilityApiFeedService;

@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMobilityApiError {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private MobilityApiFeedService srvc;
	
	@Test
	public void test401() {
		srvc.getGtfsFeed("mdb-990");
	}
	
}
