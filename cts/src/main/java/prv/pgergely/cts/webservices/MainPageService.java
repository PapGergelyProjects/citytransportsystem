package prv.pgergely.cts.webservices;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.cts.domain.SearchValues;


@RestController
@RequestMapping(path="/api")
public class MainPageService {
	
	@GetMapping(path="/hello/{yourName}")
	public String getMessage(@PathVariable String yourName) {
		return "Hello Servlet "+yourName;
	}
	
	@GetMapping(path="/stop_location", consumes = "application/json")
	public String getLocationWithStop(@RequestBody SearchValues vals) {
		return "Some more";
	}
	
	@RequestMapping(path="**/{urlPart}", method={RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<DefaultResponse> getForAnyRequest(@PathVariable String urlPart){
		DefaultResponse resp = new DefaultResponse();
		resp.message = "The service currently is not available";
		resp.urlPart = urlPart;
		resp.statusCode = HttpStatus.FORBIDDEN.value();
		
		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.FORBIDDEN);
	}
}
