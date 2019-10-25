package prv.pgergely.ctscountry.configurations;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Bean
	@ConfigurationProperties("spring.primary-datasource")
	public DataSource getDs() {
		DataSourceBuilder dsBuilder = DataSourceBuilder.create();
		return dsBuilder.build();
	}
}
