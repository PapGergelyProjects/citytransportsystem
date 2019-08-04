package prv.pgergely.ctscountry.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.ctscountry.domain.SelectedFeed;
import prv.pgergely.ctscountry.interfaces.FeedVersionService;
import prv.pgergely.ctscountry.model.FeedVersion;

@RestController
@RequestMapping(path="/")
public class CountryService {
	
	@Autowired
	private FeedVersionService feedVersion;
	
	@GetMapping(path="/versions")
	public List<FeedVersion> getVersion() {
		return feedVersion.getFeedVersions();
	}
	
	@PostMapping(path="/register_feed", consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public String insertVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, true);
		feedVersion.insert(version);
		return "{'status': 'ok'}";
	}
	
	@PutMapping(path="/update_feed", consumes = "application/json;charset=UTF-8")
	public void updateVersion(@RequestBody SelectedFeed vers) {
		FeedVersion version = new FeedVersion(vers, false);
		feedVersion.update(version);
	}
	
	@DeleteMapping(path="/delete_feed/{feedId}")
	public String deleteVersion(@PathVariable long feedId) {
		FeedVersion version = new FeedVersion(feedId);
		feedVersion.deleteFeedVersion(version);
		return "OK";
	}
}
