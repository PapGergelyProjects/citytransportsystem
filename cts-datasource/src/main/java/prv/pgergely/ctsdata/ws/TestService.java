package prv.pgergely.ctsdata.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.ctsdata.model.Schema;

@RestController
@RequestMapping(path="/")
public class TestService {
	
	@Autowired
	private Schema schema;
	
	@GetMapping("/status")
	public ResponseEntity<Void> getState() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-schema-name", schema.getSchemaName());
		headers.set("X-status","Connected");
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);
	}
	
}
