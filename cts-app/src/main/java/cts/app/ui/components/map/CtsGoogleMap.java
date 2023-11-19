package cts.app.ui.components.map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import prv.pgergely.cts.common.domain.Coordinate;

@Tag("cts-google-map")
@JsModule("./src/cts-google-map.ts")
@NpmPackage(value = "google-maps", version = "4.3.3")
public class CtsGoogleMap extends Component {
	
	public CtsGoogleMap() {}
	
	public CtsGoogleMap(String apiKey) {
		this.getElement().setProperty("apiKey", apiKey);
	}
	
	public void initMap(InitMapData data) {
		this.getElement().callJsFunction("initMap", data.getAsJson());
	}
	
	public void addMarker(String title, Coordinate coordinate) {
		this.getElement().callJsFunction("addMarker", title, coordinate.getAsJson());
	}
	
	public void removeMarkers() {
		this.getElement().callJsFunction("removeMarkers");
	}
}
