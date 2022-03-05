package prv.pgergely.cts.ui.utils;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;

public class GoogleMapService {
	
	private GoogleMap map;

	public GoogleMapService(GoogleMap map) {
		this.map = map;
	}
	
	public GoogleMap getMapInstance() {
		return map;
	}
	
}
