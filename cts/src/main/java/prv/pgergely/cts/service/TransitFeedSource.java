package prv.pgergely.cts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.domain.FeedLocationList;
import prv.pgergely.cts.config.SourceTemplateConfig;
import prv.pgergely.cts.domain.AvailableLocation;
import prv.pgergely.cts.domain.TransitFeedView;

@Service
public class TransitFeedSource {
	
	@Autowired
	@Qualifier(SourceTemplateConfig.DEFAULT_TEMPLATE)
	private RestTemplate template;
	
	public List<TransitFeedView> getTransitFeeds() {
		ResponseEntity<FeedLocationList> resp = template.getForEntity("https://localhost:9443/cts-country/transit-feed/feeds/all", FeedLocationList.class);
		FeedLocationList res =  resp.getBody();
		return res.getFeeds().stream().map(m -> {
			TransitFeedView view = new TransitFeedView();
			view.setId(m.id);
			view.setTitle(m.title);
			view.setFeedTitle(m.feed.title);
			view.setLatest(m.feed.latest);
			view.setEnabled(m.isEnabled);
			
			return view;
		}).collect(Collectors.toList());
	}
	
	public List<AvailableLocation> getRegisteredLocations(){
		ResponseEntity<FeedLocationList> resp = template.getForEntity("https://localhost:9443/cts-country/transit-feed/feeds/registered", FeedLocationList.class);
		return resp.getBody().getFeeds().stream().map(m -> {
			AvailableLocation loc = new AvailableLocation();
			loc.setId(m.id);
			loc.setLocationName(m.title);
			loc.setLat(m.lat);
			loc.setLon(m.lon);
			
			return loc;
		}).collect(Collectors.toList());
	}
}
