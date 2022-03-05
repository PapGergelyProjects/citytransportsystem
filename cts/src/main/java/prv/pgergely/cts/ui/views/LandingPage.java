package prv.pgergely.cts.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import prv.pgergely.cts.config.CtsConfig;
import prv.pgergely.cts.ui.MainLayout;

@PageTitle("Landing Page")
@Route(value = "main", layout = MainLayout.class)
//@Tag("landing-page-view")
public class LandingPage extends VerticalLayout {
	
	@Autowired
	private CtsConfig config;
	
    private TextField name;
    private Button sayHello;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setMargin(true);
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });

        HorizontalLayout mapLayout = createMap();
        mapLayout.setSizeFull();
        this.add(name, sayHello);
        this.add(mapLayout);
	}
	
	private HorizontalLayout createMap() {
		GoogleMap gMap = new GoogleMap(config.getGoogleApiKey(), "", "");
		gMap.setSizeFull();
		gMap.setMapType(MapType.ROADMAP);
		gMap.setCenter(new LatLon(47.497913D, 19.040236D));
		
		return new HorizontalLayout(gMap);
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
	}

}
