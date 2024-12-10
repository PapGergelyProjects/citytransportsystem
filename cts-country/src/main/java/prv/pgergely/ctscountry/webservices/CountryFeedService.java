package prv.pgergely.ctscountry.webservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.FeedLocationList;
import prv.pgergely.cts.common.domain.ResponseData;
import prv.pgergely.cts.common.domain.SelectedFeed;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.services.DatasourceService;
import prv.pgergely.ctscountry.services.FeedSource;
import prv.pgergely.ctscountry.services.TransitFeedLocationSource;

@RestController
@RequestMapping(path="/transit-feed")
public class CountryFeedService {

	@Autowired
	private FeedSource src;
	
	@Autowired
	private TransitFeedLocationSource locationSrc;
	
	@Autowired
	private DatasourceService dsService;
	
	@GetMapping(path="/hello")
	public String greet() {
		return "Test Works";
	}
	
	@GetMapping(path="/feeds/{locationState}", produces = "application/json")
	public ResponseEntity<FeedLocationList> getFeeds(@PathVariable String locationState){
		try {
			return switch (locationState) {
				case "all": {
					yield new ResponseEntity<FeedLocationList>(new FeedLocationList(locationSrc.getLocations(false)), HttpStatus.OK);
				}
				case "registered":{
					yield new ResponseEntity<FeedLocationList>(new FeedLocationList(locationSrc.getLocations(true)), HttpStatus.OK);
				}
				default:
					throw new IOException();
				};
		} catch (IOException e) {
			return new ResponseEntity<FeedLocationList>(new FeedLocationList(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path="/feeds/id/{feedId}", produces = "application/json")
	public ResponseEntity<Feeds> getFeed(@PathVariable Long feedId) {
		try {
			return new ResponseEntity<Feeds>(src.getFeed(feedId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Feeds>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping(path="/feeds/update-state")
	public ResponseEntity<ResponseData> updateSource(@RequestBody SelectedFeed feed){
		ResponseData data = new ResponseData();
		data.setId(feed.getId());
		try {
			dsService.updateSource(feed.getId(), feed.getState());
			data.setMessage("state update successful");
			return new ResponseEntity<ResponseData>(data, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			data.setMessage("state update unsuccessful "+e.getMessage());
			return new ResponseEntity<ResponseData>(data, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
