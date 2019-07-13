package prv.pgergely.ctscountry.webservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping(path="/create", consumes = "application/json;charset=UTF-8")
	public String insertVersion(@RequestBody FeedVersion vers) {
		feedVersion.insert(vers);
		return "OK";
	}
	
	@PutMapping(path="/update")
	public void updateVersion(@RequestBody FeedVersion vers) {
		feedVersion.update(vers);
	}
}
