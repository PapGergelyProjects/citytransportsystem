package cts.app.ui.components.map;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import cts.app.domain.ClickOnMapEvent;
import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.observable.ObservableObject;

@Tag("cts-google-map")
@JsModule("./src/cts-google-map.ts")
@NpmPackage(value = "google-maps", version = "4.3.3")
public class CtsGoogleMap extends Component {
	
	private  ObservableObject<ClickOnMapEvent> clickObsv;
	
	public CtsGoogleMap() {}
	
	public CtsGoogleMap(String apiKey, ObservableObject<ClickOnMapEvent> obsv) {
		this.clickObsv = obsv;
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
	
	@ClientCallable
	private String clickOnMapEvent(JsonObject event) {
		ClickOnMapEvent mapEvent = new ClickOnMapEvent(Coordinate.getFromJson(event), new ArrayList<>());
		clickObsv.next(mapEvent);
		return event+" received.";
	}
	
	public void subscribe(Consumer<ClickOnMapEvent> funct) {
		clickObsv.subscribe(funct);
	}
	
	public void unsubscribe() {
		clickObsv.unsubscribe();
	}
}
