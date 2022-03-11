package prv.pgergely.cts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SourceTemplateConfig {
	
	private static final String DEFAULT_TEMPLATE = "DEFAULT_TEMPLATE";
	
	@Bean(SourceTemplateConfig.DEFAULT_TEMPLATE)
	public RestTemplate getDefaultTemplate() {
		return new RestTemplate();
	}
}
