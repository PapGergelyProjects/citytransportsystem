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
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import prv.pgergely.ctsdata.utility.Schema;

@Order(1)
@Component
public class DatabaseInit implements ApplicationRunner {
	
	@Autowired
	private ResourceLoader resourceLoad;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Schema schema;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoad).getResources("classpath:sql/*.sql");
		ResourceDatabasePopulator dataPopOthers = new ResourceDatabasePopulator();
		dataPopOthers.addScript(getReplacedSchemaWithTables());
		dataPopOthers.addScripts(resources);
		dataPopOthers.setSeparator("^;");
		DatabasePopulatorUtils.execute(dataPopOthers, dataSource);
	}
	
	private Resource getReplacedSchemaWithTables() throws IOException {
		InputStream ins = resourceLoad.getResource("classpath:sql/base/create_schema_with_tables.sql").getInputStream();
		StringBuilder sb = new StringBuilder();
		try(BufferedReader bfr = new BufferedReader(new InputStreamReader(ins))) {
			while(bfr.ready()) {
				sb.append(bfr.readLine()+"\n");
			}
		}
		String sql = sb.toString().replaceAll("<schema_name>", schema.getSchemaName());
		Resource resource = new ByteArrayResource(sql.getBytes());
		return resource;
	}

}
