package prv.pgergely.ctsdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.ctsdata.model.StopLocation;
import prv.pgergely.ctsdata.repo.StopLocationRepoImpl;

@Service
public class StopLocationService {
	
	@Autowired
	private StopLocationRepoImpl repo;
	
	public List<StopLocation> getAllStopWithinRadius(SearchLocation location) {
		return repo.getAllStopWithinRadius(location);
	}
	
	public List<StopLocation> getAllStopWithinRadiusWithTime(SearchLocation location){
		return repo.getAllStopWithinRadiusWithTime(location);
	}
}
