package prv.pgergely.ctsdata.interfaces;

import java.util.List;

import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.ctsdata.model.StopLocation;

public interface StopLocationRepo {
	
	public List<StopLocation> getAllStopWithinRadius(SearchLocation location);
	public List<StopLocation> getAllStopWithinRadiusWithTime(SearchLocation location);
	
}
