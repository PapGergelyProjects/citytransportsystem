package prv.pgergely.ctscountry.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Value("${db.driver.class}")
	private String driverClass;
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.username}")
	private String username;
	
	@Value("${db.password}")
	private String password;
	
	@Bean
	public DataSource getDs() {
		DataSourceBuilder dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(driverClass);
		dsBuilder.url(url);
		dsBuilder.username(username);
		dsBuilder.password(password);
		return dsBuilder.build();
	}
	
}
