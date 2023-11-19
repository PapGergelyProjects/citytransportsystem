package prv.pgergely.cts.common.domain;

import java.io.Serializable;

import elemental.json.Json;
import elemental.json.JsonObject;

public class Coordinate implements Serializable {
	
	private final double latitude;
	private final double longitude;
	
	public Coordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public JsonObject getAsJson() {
		JsonObject obj = Json.createObject();
		obj.put("lat", this.latitude);
		obj.put("lng", this.longitude);
		
		return obj;
	}
	
	@Override
	public String toString() {
		return "Coordinate(latitude=" + latitude + ", longitude=" + longitude + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 7;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
}
