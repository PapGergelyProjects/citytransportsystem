package cts.app.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cts.app.config.SourceTemplateConfig;
import cts.app.domain.StopLocationWrapper;
import prv.pgergely.cts.common.domain.SearchLocation;

@Service
public class TransportDataService {
	
	@Autowired
	@Qualifier(SourceTemplateConfig.DEFAULT_TEMPLATE)
	private RestTemplate template;
	
	public StopLocationWrapper getStopsAndTimes(String dsUrl, SearchLocation searchVal){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//headers.set("X-Feed", searchVal.get);
		//headers.set("X-Mode", "Activate");
		HttpEntity<SearchLocation> entity = new HttpEntity<>(searchVal, headers);
		ResponseEntity<StopLocationWrapper> resp = template.postForEntity(dsUrl+"/api/stops/locations", entity, StopLocationWrapper.class);
		
		return resp.getBody();
	}
}
