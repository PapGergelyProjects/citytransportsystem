package prv.pgergely.ctsdata.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatasourceConfig{
	
	@Bean
	@Profile("dev")
	@ConfigurationProperties("spring.datasource-devel")
	public DataSource getDatasourceDev() {
		DataSourceBuilder dsBuild = DataSourceBuilder.create();
		return dsBuild.build();
	}
	
	@Bean
	@Profile("prod")
	@ConfigurationProperties("spring.datasource-primary")
	public DataSource getDatasourceProd() {
		DataSourceBuilder dsBuild = DataSourceBuilder.create();
		return dsBuild.build();
	}
}
