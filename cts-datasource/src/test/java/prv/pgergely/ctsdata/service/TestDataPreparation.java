package prv.pgergely.ctsdata.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

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

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationCtsDatasource.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestDataPreparation {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DataPreparation prepare;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	//@Test
	public void testDataLoader() throws IOException, CannotGetJdbcConnectionException, SQLException {
		InputStream stream = resourceLoader.getResource("classpath:test/data2.zip").getInputStream();
		byte[] array = new byte[stream.available()];
		stream.read(array);
		prepare.extractZipFile(array);
	}
	
	@Test
	@DisplayName("CSV generator")
	public void testCsvContentGenerator() throws IOException {
		InputStream stream = resourceLoader.getResource("classpath:test/stops.txt").getInputStream();
		prepare.prepareCsvContentForTest("stops.txt", stream);
	}
}
