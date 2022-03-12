package prv.pgergely.cts.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.config.SourceTemplateConfig;
import prv.pgergely.cts.domain.ResponseData;
import prv.pgergely.cts.domain.SelectedFeed;

@Service
public class FeedOperationService {
	
	@Autowired
	@Qualifier(SourceTemplateConfig.DEFAULT_TEMPLATE)
	private RestTemplate template;
	
	public ResponseData registerFeed(SelectedFeed actFeed) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-Feed", actFeed.getTechnicalTitle());
		HttpEntity<SelectedFeed> entity = new HttpEntity<>(actFeed, headers);
		ResponseEntity<ResponseData> resp =  template.postForEntity("https://localhost:9443/cts-country/feed/register", entity, ResponseData.class);
		
		return resp.getBody();
	}
	
	public void deleteFeed(Long id) {
		template.delete("https://localhost:9443/cts-country/feed/delete/"+id);
	}
}
