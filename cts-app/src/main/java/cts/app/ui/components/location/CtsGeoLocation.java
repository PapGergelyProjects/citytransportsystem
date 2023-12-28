package cts.app.ui.components.location;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import cts.app.domain.Position;
import elemental.json.JsonObject;

@Tag("cts-geo-location")
@JsModule("./src/cts-geo-location.ts")
public class CtsGeoLocation extends Component {
	
	public void updateActualLocation() {
		this.getElement().callJsFunction("updateLocation");
	}
	
	@ClientCallable
	private void obtainPosition(JsonObject event) {
		Position actPos = Position.getFromJson(event);
		System.out.println("Act pos: "+actPos);
	}
}
