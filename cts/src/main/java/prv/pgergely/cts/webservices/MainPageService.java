package prv.pgergely.cts.webservices;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.commsystem.HttpCommSystem;
import prv.pgergely.cts.domain.SearchValues;


@RestController
@RequestMapping(path="/")
public class MainPageService {
	
	@Autowired
	private HttpCommSystem comm;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping(path="/hello/{yourName}")
	public String getMessage(@PathVariable String yourName) {
		return "Hello Servlet "+yourName;
	}
	
	
	@GetMapping(path="/stop_location", consumes = "application/json")
	public String getLocationWithStop(@RequestBody SearchValues vals) {
		try {
			comm.getRequest("");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Some more";
	}
}
