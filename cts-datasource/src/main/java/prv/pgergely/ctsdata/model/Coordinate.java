package prv.pgergely.ctsdata.model;


/**
 * This class represents a coordinate.
 * Mainly use in other classes
 * 
 * @author Pap Gergely
 *
 */
public class Coordinate{
	
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "Coordinate(latitude=" + latitude + ", longitude=" + longitude + ")";
	}
}
