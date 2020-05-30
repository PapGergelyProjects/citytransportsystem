package prv.pgergely.ctscountry.modules;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
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
		Resource initSchema = resourceLoad.getResource("classpath:sql/create_db_env.sql");
		ResourceDatabasePopulator dataPop = new ResourceDatabasePopulator(initSchema);
		dataPop.setSeparator("^;");
		DatabasePopulatorUtils.execute(dataPop, dataSource);
	}
	
}
