package prv.pgergely.cts.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.elmot.flow.sensors.GeoLocation;
import org.vaadin.elmot.flow.sensors.Position;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap.MapType;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapMarker;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPolygon;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.AvailableLocation;
import prv.pgergely.cts.service.TransitFeedSource;
import prv.pgergely.cts.ui.MainLayout;
import prv.pgergely.cts.ui.utils.FlexSearchLayout;
import prv.pgergely.cts.utils.Calculations;

@UIScope
@SpringComponent
@PageTitle("CTS - Map")
@Route(value = "main", layout = MainLayout.class)
public class LandingPage extends VerticalLayout {
	
	private static final long serialVersionUID = -4689904492954301366L;

	@Autowired
	private GoogleMap gMap;
	
	@Autowired
	private TransitFeedSource source;
	private GeoLocation geoLocation;
	private ComboBox<AvailableLocation> loadedLocations;
	private IntegerField searchRadius;
	private GoogleMapPolygon circle;
	private GoogleMapMarker centerMarker;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
        HorizontalLayout mapLayout = createMap();
        mapLayout.setSizeFull();
        this.add(initSearchBar());
        this.add(mapLayout);
        this.add(location());
	}
	
	private FlexSearchLayout initSearchBar() {
		loadedLocations = new ComboBox<>("Available Locations", source.getRegisteredLocations());
		loadedLocations.addValueChangeListener(event -> {
			AvailableLocation selected = event.getValue();
			if(selected != null) {
				gMap.setCenter(new LatLon(selected.getLat(), selected.getLon()));
			}
		});
		searchRadius = new IntegerField("Search Radius");
		searchRadius.setRequiredIndicatorVisible(true);
		Button currentLocation = new Button("Current Location", VaadinIcon.PIN.create());
		currentLocation.addClickListener(event ->{
			Position pos = geoLocation.getValue();
			if(pos != null) {
				gMap.setZoom(15);
				gMap.setCenter(new LatLon(pos.getLatitude(), pos.getLongitude()));
			} else {
				Notification.show("The current location is not available.", 5000, com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER);
			}
		});
		FlexSearchLayout layout = new FlexSearchLayout(loadedLocations, searchRadius, currentLocation);
		layout.setGap("10px");
		
		return layout;
	}
	
	private HorizontalLayout createMap() {
		gMap.setSizeFull();
		gMap.setMapType(MapType.ROADMAP);
		gMap.addClickListener(event -> {
			final Double lat = event.getLatitude();
			final Double lon = event.getLongitude();
			final Integer radius = searchRadius.getValue();
			if(radius != null) {
				if(circle != null && centerMarker != null) {
					gMap.removePolygon(circle);
					gMap.removeMarker(centerMarker);
				}
				circle = gMap.addPolygon(Calculations.calcArcPoints(lat, lon, radius/2));
				circle.setClosed(false);
				centerMarker = gMap.addMarker("You stand here", new LatLon(lat, lon), false, "");
			} else {
				Notification.show("Invalid radius value.", 5000, com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER);
			}
		});
		//gMap.setCenter(new LatLon(47.497913D, 19.040236D));
		return new HorizontalLayout(gMap);
	}
	
	private GeoLocation location() {
		geoLocation = new GeoLocation();
		geoLocation.setWatch(true);
		geoLocation.setHighAccuracy(true);
		geoLocation.setTimeout(100000);
		geoLocation.setMaxAge(200000);
		geoLocation.addValueChangeListener( e->{
			Position pos = e.getValue();
		});
		geoLocation.addErrorListener( e->{
			
		});
		
		return geoLocation;
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		loadedLocations.setDataProvider(new ListDataProvider<>(source.getRegisteredLocations()));
	}

}
