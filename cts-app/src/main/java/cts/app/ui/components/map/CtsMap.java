package cts.app.ui.components.map;

import com.vaadin.flow.component.html.Div;

import prv.pgergely.cts.common.domain.Coordinate;

public class CtsMap extends Div {
	
	private CtsGoogleMap map;
	
	public CtsMap() {
        this.setHeight("500px");
        this.setWidth("800px");
	}
	
	public void initMap(InitMapData params) {
        map = new CtsGoogleMap(params.getApiKey());
        map.initMap(params);
        this.add(map);
	}
	
	public void addMarker(String title, Coordinate coordinate) {
		this.map.addMarker(title, coordinate);
	}
	
	public void removeMarkers() {
		this.map.removeMarkers();
	}
	
	public CtsGoogleMap getMap() {
		return map;
	}
}
