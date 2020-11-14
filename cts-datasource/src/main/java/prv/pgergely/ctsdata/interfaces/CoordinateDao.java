package prv.pgergely.ctsdata.interfaces;

import java.util.List;

import prv.pgergely.cts.common.domain.Coordinate;

public interface CoordinateDao {
	
	public Coordinate getCoordinate(long id);
	public List<Coordinate> getRadiusCoordinates(double lat, double lon, int radius);
}
