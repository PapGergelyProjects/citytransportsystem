package cts.app.service;

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

import cts.app.config.CtsConfig;
import cts.app.config.SourceTemplateConfig;
import cts.app.domain.AvailableLocation;
import cts.app.domain.ResponseData;
import cts.app.domain.TransitFeedView;
import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.FeedLocationList;
import prv.pgergely.cts.common.domain.SelectedFeed;
import prv.pgergely.cts.common.domain.SourceState;

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
			view.setId(m.getId());
			view.setTitle(m.getTitle());
			view.setFeedTitle(m.getFeed().getTitle());
			view.setLatest(m.getFeed().getLatest());
			view.setEnabled(m.isEnabled());
			view.setActive(m.isActive());
			view.setState(m.getState());
			
			return view;
		}).collect(Collectors.toList());
	}
	
	public List<AvailableLocation> getRegisteredLocations() throws RestClientException{
		ResponseEntity<FeedLocationList> resp = template.getForEntity(config.getServiceUrl()+"/transit-feed/feeds/registered", FeedLocationList.class);
		return resp.getBody().getFeeds().stream().filter(p -> p.isActive()).map(m -> {
			AvailableLocation loc = new AvailableLocation();
			loc.setId(m.getId());
			loc.setLocationName(m.getTitle());
			loc.setDsUrl(m.getDsUrl());
			loc.setLat(m.getLat());
			loc.setLon(m.getLon());
			
			return loc;
		}).collect(Collectors.toList());
	}
}
