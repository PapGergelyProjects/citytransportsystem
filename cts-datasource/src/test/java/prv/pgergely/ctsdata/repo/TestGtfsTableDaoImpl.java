package prv.pgergely.ctsdata.repo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.ActiveProfiles;

import prv.pgergely.ctsdata.config.ApplicationCtsDatasource;
import prv.pgergely.ctsdata.service.GtfsTablesService;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationCtsDatasource.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestGtfsTableDaoImpl {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private GtfsTablesService impl;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@BeforeAll
	public static void before() {
		
	}
	
	@Test
	@DisplayName("Test COPY statement")
	public void copy() throws CannotGetJdbcConnectionException, SQLException, IOException {
		InputStream csvStream = resourceLoader.getResource("classpath:test/copy.csv").getInputStream();
		impl.copy("COPY routes (agency_id,route_id,route_short_name,route_long_name,route_type,route_desc,route_color,route_text_color) FROM STDIN WITH CSV DELIMITER ',' HEADER", csvStream);
	}
}
