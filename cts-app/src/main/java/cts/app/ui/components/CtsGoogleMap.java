package cts.app.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@Tag("cts-google-map")
@JsModule("./src/cts-google-map.ts")
@NpmPackage(value = "google-maps", version = "4.3.3")
public class CtsGoogleMap extends Component {
	
	public CtsGoogleMap() {}
	
	public CtsGoogleMap(String apiKey) {
		this.getElement().setProperty("apiKey", apiKey);
	}
	
	public void initMap() {
		this.getElement().callJsFunction("initMap");
	}
}
