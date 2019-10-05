package prv.pgergely.ctsdata.ws;

import java.time.LocalDateTime;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.cts.common.domain.TransitFeedZipFile;

@RestController
@RequestMapping("/api/")
public class DatasourceApi {
	
	@Autowired
	private Queue<TransitFeedZipFile> internalStore;
	
	@PostMapping(path="/send_zip")
	public ResponseEntity<DefaultResponse> getZipPackage(@RequestBody TransitFeedZipFile zipFile){
		DefaultResponse resp = new DefaultResponse();
		resp.message = "Package has been transferred";
		resp.timestamp = LocalDateTime.now();
		
		internalStore.add(zipFile);
		
		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.ACCEPTED);
	}
	
}
