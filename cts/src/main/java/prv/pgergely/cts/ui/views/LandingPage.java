package prv.pgergely.cts.ui.views;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.AvailableLocation;
import prv.pgergely.cts.ui.MainLayout;

@UIScope
@SpringComponent
@PageTitle("CTS - Map")
@Route(value = "main", layout = MainLayout.class)
public class LandingPage extends VerticalLayout {
	
	private static final long serialVersionUID = -4689904492954301366L;

	@Autowired
	private GoogleMap gMap;
	
    private ComboBox<AvailableLocation> loadedLocations; 
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		loadedLocations = new ComboBox<>("Available Locations", new ArrayList<>());
        HorizontalLayout mapLayout = createMap();
        mapLayout.setSizeFull();
        this.add(loadedLocations);
        this.add(mapLayout);
	}
	
	private HorizontalLayout createMap() {
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
