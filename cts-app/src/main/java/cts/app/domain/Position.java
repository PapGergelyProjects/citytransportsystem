package cts.app.domain;

import java.io.Serializable;

import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;

public class Position extends Coordinate implements Serializable{
	
	private Double accuracy;
	private Double speed;
	
	private Position(Double lat, Double lon, Double accuracy, Double speed) {
		super(lat, lon);
		this.accuracy = accuracy;
		this.speed = speed;
	}
	
	public static Position getFromJson(JsonObject event) {
		return new Position(event.get("lat").asNumber(), event.get("lon").asNumber(), event.get("accuracy").asNumber(), event.get("speed")==null ? null : event.get("speed").asNumber());
	}
	
	@Override
	public String toString() {
		return "Position {\naccuracy:" + accuracy + ", \nspeed:" + speed + ", \ngetLatitude():" + getLatitude()
				+ ", \ngetLongitude():" + getLongitude() + "\n}";
	}
	
	
}
