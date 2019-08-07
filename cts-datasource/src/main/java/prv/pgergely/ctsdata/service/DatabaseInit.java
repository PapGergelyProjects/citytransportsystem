package prv.pgergely.ctsdata.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInit implements ApplicationRunner {
	
	@Autowired
	private ResourceLoader resourceLoad;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Resource[] resourceArray = {
				resourceLoad.getResource("classpath:sql/create_schema_with_tables.sql"),
				resourceLoad.getResource("classpath:sql/clear_tables.sql"),
				resourceLoad.getResource("classpath:sql/arcpoints.sql"),
				resourceLoad.getResource("classpath:sql/m_view_static_stops_with_times.sql"),
				resourceLoad.getResource("classpath:sql/m_view_static_stops.sql"),
				resourceLoad.getResource("classpath:sql/point_in_range.sql"),
				resourceLoad.getResource("classpath:sql/stops_and_times_within_radius.sql"),
				resourceLoad.getResource("classpath:sql/stops_within_radius.sql")
		};
		ResourceDatabasePopulator dataPop = new ResourceDatabasePopulator(resourceArray);
		dataPop.setSeparator("^;");
		DatabasePopulatorUtils.execute(dataPop, dataSource);
	}

}
