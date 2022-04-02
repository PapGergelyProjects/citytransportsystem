package prv.pgergely.ctsdata.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig{
	
	@Bean
	@ConfigurationProperties("spring.datasource-secondary")
	public DataSource getDatasource() {
		DataSourceBuilder dsBuild = DataSourceBuilder.create();
		return dsBuild.build();
	}
}
