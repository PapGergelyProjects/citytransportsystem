package cts.app.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import cts.app.domain.AvailableLocation;
import cts.app.domain.Position;
import cts.app.domain.Radius;
import cts.app.domain.StopLocation;
import cts.app.domain.StopLocationWrapper;
import cts.app.service.FeedService;
import cts.app.service.TransportDataService;
import cts.app.ui.MainLayout;
import cts.app.ui.components.location.CtsGeoLocation;
import cts.app.ui.components.map.CtsGoogleMap;
import cts.app.ui.components.map.MapMarker;
import cts.app.ui.utils.CtsNotification;
import cts.app.ui.utils.FlexSearchLayout;
import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.domain.SearchLocation;

@UIScope
@SpringComponent
@PreserveOnRefresh
@PageTitle("CTS - Map")
@Route(value = "main", layout = MainLayout.class)
public class LandingPage extends VerticalLayout {
	
	private static final long serialVersionUID = -4689904492954301366L;
	
	@Autowired
	private CtsGoogleMap map;
	
	@Autowired
	private FeedService source;
	
	@Autowired
	private TransportDataService dataSrvc;
	
	@Autowired
	private CtsNotification noti;
	
	private CtsGeoLocation ctsGeoLoc;
	private ComboBox<AvailableLocation> loadedLocations;
	private IntegerField searchRadius;
	private Binder<Radius> fieldBinder;
	private Radius radiusBean;
	
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
        mapLayout.setWidth("100%");
        ctsGeoLoc = new CtsGeoLocation();
        this.add(searchPanel, mapLayout, ctsGeoLoc);
	}
	
	private FlexSearchLayout initSearchBar() {
		loadedLocations = new ComboBox<>("Available Locations", getAvailableLocations());
		//loadedLocations.setItems(new ListDataProvider<>(getAvailableLocations()));
		loadedLocations.addValueChangeListener(event -> {
			AvailableLocation selected = event.getValue();
			if(selected != null) {
				map.setCenter(new Coordinate(selected.getLat(), selected.getLon()));
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
			Position pos = ctsGeoLoc.getLastPosition();
			if(pos != null && fieldBinder.isValid()) {
				pos.setRadius(searchRadius.getValue()/2);
				setPositionOnMap(pos);
				setStopMarkersOnMap(pos);
			} else {
				noti.showNotification(NotificationVariant.LUMO_ERROR, "The current location is not available.");
			}
		});
		FlexSearchLayout layout = new FlexSearchLayout(loadedLocations, searchRadius, currentLocation);
		layout.setGap("10px");
		
		return layout;
	}
	
	private HorizontalLayout createMap() {
		map.addClickListener(event -> {
			Coordinate c = event.getIncomingCoord();
			c.setRadius(searchRadius.getValue());
			setPositionOnMap(c);
			setStopMarkersOnMap(c);
		});
		Div mapDiv = new Div();
		mapDiv.setId("googleMapDiv");
		mapDiv.setHeight("100%");
		mapDiv.setWidth("100%");
		mapDiv.add(map);
		
		return new HorizontalLayout(mapDiv);
	}
	
	private void setPositionOnMap(final Coordinate actPosition) {
		if(fieldBinder.isValid()) {
			map.removeMarkers();
			map.setCenter(actPosition);
			map.drawCircle(actPosition);
		} else {
			noti.showNotification(NotificationVariant.LUMO_ERROR, "Invalid radius value.");
		}
	}
	
	private void setStopMarkersOnMap(final Coordinate actPosition) {
		AvailableLocation selected = loadedLocations.getValue();
		if(selected != null) {
			SearchLocation loc = new SearchLocation();
			loc.setRadius(actPosition.getRadius());
			loc.setCoordinates(actPosition);
			StopLocationWrapper stops = dataSrvc.getStopsAndTimes(selected.getDsUrl(), loc);
			for(StopLocation stop : stops.getStopList()) {
				Coordinate stopCoord = stop.getStopCoordinate();
				map.addCustomMarker(new MapMarker(stop.getStopName(), stop.getStopColor(), stopCoord));
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
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		fieldBinder.readBean(radiusBean);
		map.restoreMarkers();
	}
	
}
