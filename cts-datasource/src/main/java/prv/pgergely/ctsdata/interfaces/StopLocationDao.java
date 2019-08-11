package prv.pgergely.ctsdata.interfaces;

import java.util.List;

import prv.pgergely.ctsdata.model.StopLocation;

public interface StopLocationDao {
	
	public List<StopLocation> getAllStopWithinRadius(double centerLat, double centerLon, double radius);
	public List<StopLocation> getAllStopWithinRadiusWithTime(double centerLat, double centerLon, double radius);
	
}
