package prv.pgergely.cts.webservices;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.commsystem.HttpCommSystem;


@RestController
@RequestMapping(path="/")
public class MainPage {
	
	@Autowired
	private HttpCommSystem comm;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping(path="/hello")
	public String getMessage() {
		return "Hello Servlet "+context.getContextPath();
	}
	
	@GetMapping("/stop_location")
	public String getLocationWithStop() {
		try {
			comm.getRequest("");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Some more";
	}
}
