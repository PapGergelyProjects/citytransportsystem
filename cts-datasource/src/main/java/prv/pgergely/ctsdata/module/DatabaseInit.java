package prv.pgergely.ctsdata.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class DatabaseInit implements ApplicationRunner {
	
	@Autowired
	private ResourceLoader resourceLoad;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Resource[] resourceArray = {
				resourceLoad.getResource("classpath:sql/clear_tables.sql"),
				resourceLoad.getResource("classpath:sql/arcpoints.sql"),
				resourceLoad.getResource("classpath:sql/m_view_static_stops_with_times.sql"),
				resourceLoad.getResource("classpath:sql/m_view_static_stops.sql"),
				resourceLoad.getResource("classpath:sql/point_in_range.sql"),
				resourceLoad.getResource("classpath:sql/stops_and_times_within_radius.sql"),
				resourceLoad.getResource("classpath:sql/stops_within_radius.sql")
		};
		ResourceDatabasePopulator dataPop = new ResourceDatabasePopulator(getReplacedSchemaWithTables());
		dataPop.setSeparator("^;");
		ResourceDatabasePopulator dataPopOthers = new ResourceDatabasePopulator(resourceArray);
		dataPopOthers.setSeparator("^;");
		DatabasePopulatorUtils.execute(dataPop, dataSource);
		DatabasePopulatorUtils.execute(dataPopOthers, dataSource);
	}
	
	private Resource getReplacedSchemaWithTables() throws IOException {
		InputStream ins = resourceLoad.getResource("classpath:sql/create_schema_with_tables.sql").getInputStream();
		StringBuilder sb = new StringBuilder();
		try(BufferedReader bfr = new BufferedReader(new InputStreamReader(ins))) {
			while(bfr.ready()) {
				sb.append(bfr.readLine()+"\n");
			}
		}
		String sql = sb.toString().replaceAll("<schema_name>", System.getProperty("schema"));
		Resource resource = new ByteArrayResource(sql.getBytes());
		return resource;
	}

}
