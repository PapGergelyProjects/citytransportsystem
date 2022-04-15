package prv.pgergely.cts.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.domain.FeedLocationList;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.config.CtsConfig;
import prv.pgergely.cts.config.SourceTemplateConfig;
import prv.pgergely.cts.domain.AvailableLocation;
import prv.pgergely.cts.domain.ResponseData;
import prv.pgergely.cts.domain.SelectedFeed;
import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.utils.SourceStates;

@Service
public class FeedService {
	
	@Autowired
	@Qualifier(SourceTemplateConfig.DEFAULT_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	private CtsConfig config;
	
	public ResponseData registerFeed(SelectedFeed actFeed) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-Feed", actFeed.getTechnicalTitle());
		headers.set("X-Mode", "Register");
		HttpEntity<SelectedFeed> entity = new HttpEntity<>(actFeed, headers);
		ResponseEntity<ResponseData> resp =  template.postForEntity(config.getServiceUrl()+"/feed/register", entity, ResponseData.class);//TODO: config file
		
		return resp.getBody();
	}
	
	public ResponseData startFeed(SelectedFeed actFeed) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-Feed", actFeed.getTechnicalTitle());
		headers.set("X-Mode", "Activate");
		HttpEntity<SelectedFeed> entity = new HttpEntity<>(actFeed, headers);
		ResponseData resp = template.patchForObject(config.getServiceUrl()+"/feed/update", entity, ResponseData.class);
		
		return resp;
	}
	
	public void deleteFeed(Long id) {
		template.delete(config.getServiceUrl()+"/feed/delete/"+id);
	}
	
	public List<TransitFeedView> getTransitFeeds() throws RestClientException {
		ResponseEntity<FeedLocationList> resp = template.getForEntity(config.getServiceUrl()+"/transit-feed/feeds/all", FeedLocationList.class);
		FeedLocationList res =  resp.getBody();
		return res.getFeeds().stream().map(m -> {
			TransitFeedView view = new TransitFeedView();
			view.setId(m.id);
			view.setTitle(m.title);
			view.setFeedTitle(m.feed.title);
			view.setLatest(m.feed.latest);
			view.setEnabled(m.isEnabled);
			view.setActive(m.isActive);
			view.setState(new SourceState(m.id, m.schemaName, SourceStates.getByBooleans(m.isEnabled, m.isActive, false).name()));
			
			return view;
		}).collect(Collectors.toList());
	}
	
	public List<AvailableLocation> getRegisteredLocations() throws RestClientException{
		ResponseEntity<FeedLocationList> resp = template.getForEntity(config.getServiceUrl()+"/transit-feed/feeds/registered", FeedLocationList.class);
		return resp.getBody().getFeeds().stream().filter(p -> p.isActive).map(m -> {
			AvailableLocation loc = new AvailableLocation();
			loc.setId(m.id);
			loc.setLocationName(m.title);
			loc.setDsUrl(m.dsUrl);
			loc.setLat(m.lat);
			loc.setLon(m.lon);
			
			return loc;
		}).collect(Collectors.toList());
	}
}
