package prv.pgergely.ctscountry.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:config.properties")
public class PropertyConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propConfig(){
		return new PropertySourcesPlaceholderConfigurer();
	}
}
