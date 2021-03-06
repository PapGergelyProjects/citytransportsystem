package prv.pgergely.ctscountry.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import prv.pgergely.ctscountry.domain.ResponseData;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.utils.TemplateQualifier;

@Configuration
public class ResponseBeanConfig {
	
	public Feeds getErrorFeed() {
		Feeds feed = new Feeds();
		return feed;
	}
	
	@Bean(TemplateQualifier.RESPONSE_ACCEPTED)
	public ResponseData getAcceptResponse() {
		ResponseData data = new ResponseData();
		data.statusCode = 200;
		data.status = "accepted";
		data.message="OK";
		
		return data;
	}
}
