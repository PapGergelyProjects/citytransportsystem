package prv.pgergely.ctscountry.configurations;

import org.springframework.context.annotation.Configuration;

import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;

@Configuration
public class ErrorResponseBeanConfig {
	
	public Feeds getErrorFeed() {
		Feeds feed = new Feeds();
		return feed;
	}
}
