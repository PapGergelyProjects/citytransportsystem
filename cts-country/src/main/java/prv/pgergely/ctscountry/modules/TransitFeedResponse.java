package prv.pgergely.ctscountry.modules;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctscountry.configurations.TransitFeedsTemplate;
import prv.pgergely.ctscountry.domain.SwaggerFeed;

@Component
public class TransitFeedResponse {
	
	@Autowired
	@Qualifier(TransitFeedsTemplate.TRANSITFEED_TEMPLATE)
	private RestTemplate template;
	
	@Value("${transit_feed_key}")
	private String transitApiKey;
	
	public ResponseEntity<SwaggerFeed> getFeed(int page) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<SwaggerFeed> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&page=%d&limit=100&type=gtfs", transitApiKey, page);
		
		return template.exchange(url, HttpMethod.GET, entity, SwaggerFeed.class);
	}
	
	public ResponseEntity<SwaggerFeed> getFeeds(long feedId){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<SwaggerFeed> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&location=%d&limit=100&type=gtfs", transitApiKey, feedId);
		
		return template.exchange(url, HttpMethod.GET, entity, SwaggerFeed.class);
	}
	
}
