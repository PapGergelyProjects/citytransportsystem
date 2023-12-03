package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.util.Optional;

import elemental.json.Json;
import elemental.json.JsonObject;

public class Coordinate implements Serializable {
	
	private final double latitude;
	private final double longitude;
	private Integer radius;
	
	public Coordinate() {
		this.latitude = 0.D;
		this.longitude = 0.D;
	}
	
	public Coordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Coordinate(double latitude, double longitude, Integer radius) {
		this(latitude, longitude);
		this.radius = radius;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getRadius() {
		return radius;
	}

	public JsonObject getAsJson() {
		JsonObject obj = Json.createObject();
		obj.put("lat", this.latitude);
		obj.put("lng", this.longitude);
		Optional.ofNullable(radius).ifPresent(p -> obj.put("radius", p));
		
		return obj;
	}
	
	public static Coordinate getFromJson(JsonObject obj) {
		return new Coordinate(obj.get("lat").asNumber(), obj.get("lng").asNumber());
	}
	
	@Override
	public String toString() {
		return "Coordinate {\nlatitude:" + latitude + ", \nlongitude:" + longitude + ", \nradius:" + radius + "\n}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((radius == null) ? 0 : radius.hashCode());
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
		if (radius == null) {
			if (other.radius != null)
				return false;
		} else if (!radius.equals(other.radius))
			return false;
		return true;
	}
	
}
