package cts.app.ui.components.map;

import java.io.Serializable;
import java.util.Optional;

import elemental.json.Json;
import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;

public class MapMarker implements Serializable {
	
	private final String title;
	private final String colorCode;
	private final Coordinate coordinate;

	public MapMarker(String title, String colorCode, Coordinate coordinate) {
		this.title = title;
		this.colorCode = colorCode;
		this.coordinate = coordinate;
	}
	
	public MapMarker(JsonObject object) {
		this.title = object.getString("title");
		this.colorCode = object.hasKey("colorCode") ? object.getString("colorCode") : null;  
		JsonObject coords = Json.parse(object.getObject("coordinate").toJson());
		this.coordinate = coords.hasKey("radius") ? 
				new Coordinate(coords.getNumber("lat"), coords.getNumber("lng"), Double.valueOf(coords.getNumber("radius")).intValue()) : 
				new Coordinate(coords.getNumber("lat"), coords.getNumber("lng"));
	}

	public String getTitle() {
		return title;
	}

	public String getColorCode() {
		return colorCode;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public JsonObject getAsJson() {
		JsonObject obj = Json.createObject();
		obj.put("title", title);
		Optional.ofNullable(colorCode).ifPresent(e -> obj.put("colorCode", e));
		obj.put("coordinate", coordinate.getAsJson());
		
		return obj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colorCode == null) ? 0 : colorCode.hashCode());
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		MapMarker other = (MapMarker) obj;
		if (colorCode == null) {
			if (other.colorCode != null)
				return false;
		} else if (!colorCode.equals(other.colorCode))
			return false;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{\ntitle:" + title + ", \ncolorCode:" + colorCode + ", \ncoordinate:" + coordinate + "\n}";
	}
	
}
