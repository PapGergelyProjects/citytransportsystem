package prv.pgergely.ctsdata.ws;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.ctsdata.model.StopLocation;
import prv.pgergely.ctsdata.model.StopLocationWrapper;
import prv.pgergely.ctsdata.module.DatasourceUpdater;
import prv.pgergely.ctsdata.service.StopLocationService;
import prv.pgergely.ctsdata.utility.LocationQuery;

@RestController
@RequestMapping("/api/")
public class DatasourceApi {
	
	@Autowired
	private DatasourceUpdater updater;
	
	@Autowired
	private StopLocationService stopSrvc;
	
	private Logger logger = LogManager.getLogger(DatasourceApi.class);
	
	@PostMapping(path="/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DefaultResponse> getZipPackage(@RequestBody DownloadRequest zipFile){
		logger.info("Package has arrived: "+zipFile);
		DefaultResponse resp = new DefaultResponse();
		resp.message = "Package has been transferred: "+zipFile.getFeedId();
		resp.timestamp = LocalDateTime.now();
		resp.statusCode = HttpStatus.ACCEPTED.value();
		updater.addTask(zipFile);
		
		return new ResponseEntity<DefaultResponse>(resp, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(path="/stops/{withWhat}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<StopLocationWrapper> getLocationByCoordinates(@PathVariable String withWhat, @RequestBody SearchLocation searchVals){
		List<StopLocation> res = switch (LocationQuery.getByMethod(withWhat)) {
			case WITH_TIMES: {
				yield stopSrvc.getAllStopWithinRadiusWithTime(searchVals);
			}
			case ONLY_LOCATIONS:{
				yield stopSrvc.getAllStopWithinRadius(searchVals);
			}
			default:{
				yield new ArrayList<StopLocation>();
			}
		};
		if(res.isEmpty()) {
			return new ResponseEntity<StopLocationWrapper>(new StopLocationWrapper(res), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<StopLocationWrapper>(new StopLocationWrapper(res), HttpStatus.OK);
	}
}
