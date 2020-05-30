package prv.pgergely.ctsdata.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctsdata.utility.TransitFeedZipFileInterceptor;

@Configuration
public class TemplateConfig {

	public static final String TRANSITFEED_ZIPFILE_TEMPLATE = "TRANSITFEED_ZIPFILE_TEMPLATE";
	public static final String DEFAULT_TEMPLATE = "DEFAULT_TEMPLATE";
	
	@Bean(DEFAULT_TEMPLATE)
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
	@Bean(TRANSITFEED_ZIPFILE_TEMPLATE)
	public RestTemplate getFileTemplate() {
		RestTemplate template = new RestTemplate();
		template.setInterceptors(Arrays.asList(new TransitFeedZipFileInterceptor()));
		template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		
		return template;
	}
}
