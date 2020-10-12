package prv.pgergely.ctsdata.ws;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctsdata.module.DataUpdater;
import prv.pgergely.ctsdata.module.UpdateTaskHandler;

@RestController
@RequestMapping("/api/")
public class DatasourceApi {
	
	@Autowired
	private UpdateTaskHandler updater;
	
	private Logger logger = LogManager.getLogger(DatasourceApi.class);
	
	@PostMapping(path="/receive_zip", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DefaultResponse> getZipPackage(@RequestBody DownloadRequest zipFile){
		logger.info("Package has arrived: "+zipFile);
		DefaultResponse resp = new DefaultResponse();
		resp.message = "Package has been transferred";
		resp.timestamp = LocalDateTime.now();
		resp.statusCode = HttpStatus.ACCEPTED.value();
		updater.runUpdateTask(zipFile);
		
		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.ACCEPTED);
	}
	
}
