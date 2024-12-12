package cts.app.ui.components.map;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.PreserveOnRefresh;

import cts.app.config.CtsConfig;
import cts.app.domain.ClickOnMapEvent;
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
	
	public void addMarker(String title, Coordinate coordinate) {
		this.getElement().callJsFunction("addMarker", title, coordinate.getAsJson());
	}
	
	public void addCustomMarker(String title, String colorCode, Coordinate coordinate) {
		this.getElement().callJsFunction("addCustomMarker", title, colorCode, coordinate.getAsJson());
	}
	
	public void removeMarkers() {
		this.getElement().callJsFunction("removeMarkers");
	}
	
	public void drawCircle(Coordinate coordinate) {
		this.getElement().callJsFunction("drawCircle", coordinate.getAsJson());
	}
	
	@ClientCallable
	private void clickOnMapEvent(JsonObject event) {
		ClickOnMapEvent mapEvent = new ClickOnMapEvent(Coordinate.getFromJson(event), new ArrayList<>());
		clickObsv.next(mapEvent);
	}
	
	public void addClickListener(Consumer<ClickOnMapEvent> funct) {
		clickObsv.subscribe(funct);
	}
	
	public void unsubscribe() {
		clickObsv.unsubscribe();
	}
	
}
