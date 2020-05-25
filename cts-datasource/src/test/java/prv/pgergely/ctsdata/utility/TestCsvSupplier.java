package prv.pgergely.ctsdata.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.stream.Collectors;

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
public class TestCsvSupplier {

	@LocalServerPort
	private int port;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private CsvRefiner transf;
	
	@Autowired
	private CsvRefinerGenerator supp;
	
	@Autowired
	private GtfsTablesService impl;
	
	@Test
	public void testGenerator() throws IOException, CannotGetJdbcConnectionException, SQLException {
		InputStream stream = resourceLoader.getResource("classpath:test/copy.csv").getInputStream();
		supp.setSupplierStream(stream);
		supp.setDefaultColumns(TableInsertValues.ROUTES.getColNames());
		String copy = "COPY "+TableInsertValues.ROUTES.getTableName()+" "+TableInsertValues.ROUTES.getColNames().stream().collect(Collectors.joining(",","(",")"))+" FROM STDIN WITH CSV DELIMITER ',' QUOTE '\"' ";
		for(ByteArrayOutputStream outStream = supp.get(); outStream != null; outStream = supp.get()) {
			if(outStream.size() != 0) {
				//impl.copy(copy, new ByteArrayInputStream(outStream.toByteArray()));
			}
			break;
		}
	}
}
