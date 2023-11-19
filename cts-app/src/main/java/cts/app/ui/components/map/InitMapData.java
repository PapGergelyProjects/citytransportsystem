package cts.app.ui.components.map;

import java.io.Serializable;
import java.util.Optional;

import elemental.json.Json;
import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;

public class InitMapData implements Serializable {
	
	private final String title;
	private final Coordinate coordinate;
	private final Integer zoomMagnitude;
	private final String apiKey;
	
	public InitMapData(String title, Coordinate coordinate, Integer zoomMagnitude, String apiKey) {
		this.title = title;
		this.coordinate = coordinate;
		this.zoomMagnitude = zoomMagnitude;
		this.apiKey = apiKey;
	}
	
	public String getTitle() {
		return title;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public Integer getZoomMagnitude() {
		return zoomMagnitude;
	}

	public String getApiKey() {
		return apiKey;
	}

	public JsonObject getAsJson() {
		JsonObject obj = Json.createObject();
		Optional.ofNullable(title).ifPresent(e -> obj.put("title", e));
		Optional.ofNullable(coordinate).ifPresent(e -> obj.put("coordinate", e.getAsJson()));
		Optional.ofNullable(zoomMagnitude).ifPresent(e -> obj.put("zoomMagnitude", e));
		
		return obj;
	}
	
}
