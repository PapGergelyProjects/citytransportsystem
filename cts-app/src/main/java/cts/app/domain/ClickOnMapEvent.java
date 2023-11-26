package cts.app.domain;

import java.io.Serializable;
import java.util.List;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;

public class ClickOnMapEvent implements Serializable {

	private final Coordinate incomingCoord;
	private List<Coordinate> outputCoords;
	
	public ClickOnMapEvent(Coordinate incomingCoord, List<Coordinate> outputCoords) {
		this.incomingCoord = incomingCoord;
		this.outputCoords = outputCoords;
	}

	public List<Coordinate> getOutputCoords() {
		return outputCoords;
	}
	
	/*
	 * 
	 * @deprecated don't use this
	 */
	@Deprecated
	public void setOutputCoord(Coordinate outputCoords) {
		this.outputCoords.add(outputCoords);
	}

	public Coordinate getIncomingCoord() {
		return incomingCoord;
	}
	
	public JsonArray getCoordsAsJsonArray() {
		JsonArray array = Json.createArray();
		for(int i=0; i<outputCoords.size(); i++) {
			JsonObject obj = Json.createObject();
			obj.put("lan", outputCoords.get(i).getLatitude());
			obj.put("lng", outputCoords.get(i).getLongitude());
			array.set(i, obj);
		}
		return array;
	}
}
