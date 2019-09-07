package prv.pgergely.ctscountry.configurations;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import prv.pgergely.ctscountry.interfaces.TemplateQualifier;
import prv.pgergely.ctscountry.utils.TransitFeedTemplateInterceptor;
import prv.pgergely.ctscountry.utils.TransitFeedZipFileInterceptor;

@Configuration
public class TransitFeedsTemplate {
	
	@Bean(TemplateQualifier.TRANSITFEED_TEMPLATE)
	public RestTemplate transitTemplate() {
		RestTemplate template = new RestTemplate();
		template.setInterceptors(Arrays.asList(new TransitFeedTemplateInterceptor()));
		
		return template;
	}
	
	@Bean(TemplateQualifier.TRANSITFEED_ZIPFILE_TEMPLATE)
	public RestTemplate getFileTemplate() {
		RestTemplate template = new RestTemplate();
		template.setInterceptors(Arrays.asList(new TransitFeedZipFileInterceptor()));
		template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		
		return template;
	}
	
}
