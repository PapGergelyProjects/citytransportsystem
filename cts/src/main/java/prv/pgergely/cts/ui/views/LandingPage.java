package prv.pgergely.cts.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
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
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.cts.domain.AvailableLocation;
import prv.pgergely.cts.domain.Radius;
import prv.pgergely.cts.domain.StopLocation;
import prv.pgergely.cts.domain.StopLocationWrapper;
import prv.pgergely.cts.service.FeedService;
import prv.pgergely.cts.service.TransportDataService;
import prv.pgergely.cts.ui.MainLayout;
import prv.pgergely.cts.ui.utils.CtsNotification;
import prv.pgergely.cts.ui.utils.FlexSearchLayout;
import prv.pgergely.cts.utils.Calculations;

@UIScope
@SpringComponent
//@PreserveOnRefresh
@PageTitle("CTS - Map")
@Route(value = "main", layout = MainLayout.class)
public class LandingPage extends VerticalLayout {
	
	private static final long serialVersionUID = -4689904492954301366L;

	@Autowired
	private GoogleMap gMap;
	
	@Autowired
	private FeedService source;
	
	@Autowired
	private TransportDataService dataSrvc;
	
	@Autowired
	private CtsNotification noti;
	
	private GeoLocation geoLocation;
	private ComboBox<AvailableLocation> loadedLocations;
	private IntegerField searchRadius;
	private SplitLayout searchLayout;
	private Binder<Radius> fieldBinder;
	private Radius radiusBean;
	
	private GoogleMapPolygon circle;
	private GoogleMapMarker centerMarker;
	private boolean isOpen = false;
	
	@PostConstruct
	public void init() {
		fieldBinder = new BeanValidationBinder<>(Radius.class);
		radiusBean = new Radius();
		this.setMargin(false);
		this.setSpacing(false);
		this.setSizeFull();
		Details searchPanel = new Details("Search Options", initSearchBar());
        HorizontalLayout mapLayout = createMap();
        mapLayout.setSizeFull();
        this.add(searchPanel, mapLayout, location());
	}
	
	private FlexSearchLayout initSearchBar() {
		loadedLocations = new ComboBox<>("Available Locations", getAvailableLocations());
		loadedLocations.addValueChangeListener(event -> {
			AvailableLocation selected = event.getValue();
			if(selected != null) {
				gMap.setCenter(new LatLon(selected.getLat(), selected.getLon()));
			}
		});
		searchRadius = new IntegerField("Search Radius");
		searchRadius.setRequiredIndicatorVisible(true);
		fieldBinder.forField(searchRadius).withValidator((radius,c) -> {
			if(radius == null) {
				return ValidationResult.error("Radius cannot be null");
			} else if(radius < 500 || radius > 3000) {
				return ValidationResult.error("Radius must be within 500m and 3000m");
			}
			return ValidationResult.ok();
		}).bind("radius");
		Button currentLocation = new Button("Current Location", VaadinIcon.PIN.create());
		currentLocation.addClickListener(event ->{
			Position pos = geoLocation.getValue();
			if(pos != null && fieldBinder.isValid()) {
				final Double lat = pos.getLatitude();
				final Double lon = pos.getLongitude();
				final Integer radius = searchRadius.getValue();
				setPositionOnMap(lat, lon, radius);
				setStopMarkersOnMap(lat, lon, radius);
				gMap.setCenter(new LatLon(lat, lon));
			} else {
				noti.showNotification(NotificationVariant.LUMO_ERROR, "The current location is not available.");
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
			setPositionOnMap(lat, lon, radius);
			setStopMarkersOnMap(lat, lon, radius);
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
			//Position pos = e.getValue();
		});
		geoLocation.addErrorListener( e->{
			
		});
		
		return geoLocation;
	}
	
	private void setPositionOnMap(final Double lat, final Double lon, final Integer radius) {
		if(fieldBinder.isValid()) {
			if(circle != null && centerMarker != null) {
				gMap.removePolygon(circle);
				gMap.removeMarker(centerMarker);
			}
			circle = gMap.addPolygon(Calculations.calcArcPoints(lat, lon, radius/2));
			circle.setClosed(false);
			centerMarker = gMap.addMarker("You stand here", new LatLon(lat, lon), false, "");
		} else {
			noti.showNotification(NotificationVariant.LUMO_ERROR, "Invalid radius value.");
		}
	}
	
	private void setStopMarkersOnMap(final Double lat, final Double lon, final Integer radius) {
		AvailableLocation selected = loadedLocations.getValue();
		if(selected != null) {
			SearchLocation loc = new SearchLocation();
			loc.setRadius(radius.intValue()/2);
			loc.setCoordinates(new Coordinate(lat, lon));
			StopLocationWrapper stops = dataSrvc.getStopsAndTimes(selected.getDsUrl(), loc);
			for(StopLocation stop : stops.getStopList()) {
				GoogleMapMarker markMyShit = gMap.addMarker(stop.getStopName(), new LatLon(stop.getStopCoordinate().getLatitude(), stop.getStopCoordinate().getLongitude()), false, "./images/marker_blue.png");
				gMap.addMarker(markMyShit);
			}
			
		}else {
			noti.showNotification(NotificationVariant.LUMO_ERROR, "No selected location.");
		}
	}
	
	private List<AvailableLocation> getAvailableLocations(){
		try {
			return source.getRegisteredLocations();
		} catch (RestClientException e) {
			noti.showNotification(NotificationVariant.LUMO_ERROR, "No location available.");
			return new ArrayList<>();
		}
	}
	
	private void setupSlideBtn() {
		//MainLayout.getMainInstance().getSlideBtn().addClickListener(listener);
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		fieldBinder.readBean(radiusBean);
		loadedLocations.setItems(new ListDataProvider<>(getAvailableLocations()));
		//setupSlideBtn();
	}
	
}
