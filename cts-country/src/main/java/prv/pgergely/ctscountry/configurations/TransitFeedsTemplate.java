package prv.pgergely.ctscountry.configurations;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import prv.pgergely.ctscountry.domain.SwaggerFeed;
import prv.pgergely.ctscountry.utils.TransitFeedTemplateInterceptor;

@Configuration
public class TransitFeedsTemplate {
	
	public static final String TRANSITFEED_TEMPLATE = "TRANSITFEED_TEMPLATE"; 
	
	@Bean(TRANSITFEED_TEMPLATE)
	public RestTemplate transitTemplate() {
		RestTemplate template = new RestTemplate();
		template.setInterceptors(Arrays.asList(new TransitFeedTemplateInterceptor()));
		
		return template;
	}

}
