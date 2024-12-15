package cts.app.ui.components.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.PreserveOnRefresh;

import cts.app.config.CtsConfig;
import cts.app.domain.ClickOnMapEvent;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.observable.ObservableObject;

@Tag("cts-google-map")
@PreserveOnRefresh
@JsModule("./src/cts-google-map.ts")
@NpmPackage(value = "@googlemaps/js-api-loader", version = "1.16.8")
@NpmPackage(value = "@types/google.maps", version = "^3.53.1")
public class CtsGoogleMap extends Component {
	
	private  ObservableObject<ClickOnMapEvent> clickObsv;
	private Set<MapMarker> markers = new HashSet<>();
	
	public CtsGoogleMap() {}
	
	public CtsGoogleMap(CtsConfig config, ObservableObject<ClickOnMapEvent> obsv) {
		this.clickObsv = obsv;
		this.getElement().setProperty("apiKey", config.getGoogleApiKey());
		this.getElement().setProperty("mapId", config.getGoogleMapId());
		this.getElement().setProperty("initialData", new InitMapData("Center", new Coordinate(47.497912, 19.040235), 11).getAsJson().toJson());
	}
	
	/*public void initMap(InitMapData data) {
		this.getElement().callJsFunction("initMap", data.getAsJson());
	}*/
	
	public void setCenter(Coordinate coordinate) {
		this.getElement().callJsFunction("setCenter", coordinate.getAsJson());
	}
	
	public void addMarker(MapMarker marker) {
		this.getElement().callJsFunction("addMarker", marker.getAsJson());
	}
	
	public void addCustomMarker(MapMarker marker) {
		this.getElement().callJsFunction("addCustomMarker", marker.getAsJson());
	}
	
	public void removeMarkers() {
		this.getElement().callJsFunction("removeMarkers");
	}
	
	public void drawCircle(Coordinate coordinate) {
		this.getElement().callJsFunction("drawCircle", coordinate.getAsJson());
	}
	
	public void restoreMarkers() {
		final String markerVals = markers.stream().map(m -> m.getAsJson()).collect(Collectors.toList()).toString();
		this.getElement().setProperty("previousMarkers", markerVals);
	}
	
	@ClientCallable
	private void clickOnMapEvent(JsonObject event) {
		ClickOnMapEvent mapEvent = new ClickOnMapEvent(Coordinate.getFromJson(event), new ArrayList<>());
		clickObsv.next(mapEvent);
	}
	
	@ClientCallable
	private void saveMarker(JsonObject marker) {
		markers.add(new MapMarker(marker));
	}
	
	public void addClickListener(Consumer<ClickOnMapEvent> funct) {
		clickObsv.subscribe(funct);
	}
	
	public void unsubscribe() {
		clickObsv.unsubscribe();
	}
	
}
