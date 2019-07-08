package prv.pgergely.ctscountry.configurations;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Component
public class TransitFeedsTemplate {
	
	@Value("${transit_feed_key}")
	private String transitApiKey;
	
	@Autowired
	private RestTemplate template;
	
	public ResponseEntity<SwaggerFeed> getFeed(int page) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<SwaggerFeed> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&page=%d&limit=100&type=gtfs", transitApiKey, page);
		String json = removeArrayNoti(template.exchange(url, HttpMethod.GET, entity, String.class).getBody());
		
		return new ResponseEntity<SwaggerFeed>(new Gson().fromJson(json, SwaggerFeed.class), HttpStatus.OK);
	}
	
	public ResponseEntity<SwaggerFeed> getFeeds(String transitApiKey, long feedId){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<SwaggerFeed> entity = new HttpEntity<>(headers);
		String url = String.format("http://api.transitfeeds.com/v1/getFeeds?key=%s&descendants=1&location=%d&limit=100&type=gtfs", transitApiKey, feedId);
		
		return template.exchange(url, HttpMethod.GET, entity, SwaggerFeed.class);
	}
	
	private String removeArrayNoti(String jsonFeed){
		return jsonFeed.replaceAll("\"u\":\\[\\]", "\"u\":\\{\\}");// This is a jury-rigged solution, because transit feeds api sometimes(especially when no download link) change the {} brackets to [].
	}
}
