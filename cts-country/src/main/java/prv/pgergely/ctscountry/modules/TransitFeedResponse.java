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

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.configurations.TransitFeedsTemplate;
import prv.pgergely.ctscountry.domain.TransitFeedJson;
import prv.pgergely.ctscountry.domain.TransitFeedLocationJson;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;

@Component
public class TransitFeedResponse {
	
	@Autowired
	@Qualifier(TemplateQualifier.TRANSITFEED_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	@Qualifier(TemplateQualifier.DEFAULT_TEMPLATE)
	private RestTemplate defaultTemplate;
	
	@Autowired
	private CtsConfig config;
	
	public ResponseEntity<TransitFeedJson> getFeed(int page) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<TransitFeedJson> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&page=%d&limit=100&type=gtfs", config.getTransitFeedKey(), page);
		
		return template.exchange(url, HttpMethod.GET, entity, TransitFeedJson.class);
	}
	
	public ResponseEntity<TransitFeedJson> getFeeds(long feedId){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<TransitFeedJson> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&location=%d&limit=100&type=gtfs", config.getTransitFeedKey(), feedId);
		
		return template.exchange(url, HttpMethod.GET, entity, TransitFeedJson.class);
	}
	
	public ResponseEntity<TransitFeedLocationJson> getLocations(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<TransitFeedJson> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getLocations?key=%s", config.getTransitFeedKey());
		
		return defaultTemplate.exchange(url, HttpMethod.GET, entity, TransitFeedLocationJson.class);
	} 
	
}
