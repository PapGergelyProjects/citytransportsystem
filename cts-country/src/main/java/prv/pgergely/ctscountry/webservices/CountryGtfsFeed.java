package prv.pgergely.ctscountry.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.transitfeed.FeedLocationList;
import prv.pgergely.ctscountry.services.DatasourceService;

@RestController
@RequestMapping(path="/gtfs-feed")
public class CountryGtfsFeed {

	@Autowired
	private DatasourceService dsService;
	
	@GetMapping(path="/hello")
	public String greet() {
		return "Test Works";
	}
	
	@GetMapping(path="/feeds/{locationState}", produces = "application/json")
	public ResponseEntity<FeedLocationList> getFeeds(@PathVariable String locationState){
		return ResponseEntity.ok().build();
	}
}
