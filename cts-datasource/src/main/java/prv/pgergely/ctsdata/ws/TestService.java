package prv.pgergely.ctsdata.ws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/")
public class TestService {
	
	@GetMapping("/state")
	public String getState() {
		return "OK";
	}
	
}
