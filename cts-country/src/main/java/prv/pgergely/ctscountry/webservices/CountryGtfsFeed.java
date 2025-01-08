package prv.pgergely.ctscountry.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.mobility.GftsFeedDataList;
import prv.pgergely.cts.common.domain.mobility.GtfsFeedData;
import prv.pgergely.ctscountry.services.DatasourceService;
import prv.pgergely.ctscountry.services.MobilityGtfsService;

@RestController
@RequestMapping(path="/gtfs-content")
public class CountryGtfsFeed {

	@Autowired
	private DatasourceService dsService;
	
	@Autowired
	private MobilityGtfsService gtfsSrvc;
	
	@GetMapping(path="/hello")
	public String greet() {
		return "Test Works";
	}
	
	@GetMapping(path="/feeds/{countryCode}", produces = "application/json")
	public ResponseEntity<GftsFeedDataList> getFeeds(@PathVariable String countryCode){
		List<GtfsFeedData> selectedFeeds = gtfsSrvc.getFeedsByCountry(countryCode);
		return ResponseEntity.ok(new GftsFeedDataList(selectedFeeds));
	}
}
