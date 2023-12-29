package cts.app.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import cts.app.ui.MainLayout;
import cts.app.ui.components.DummyComps;
import cts.app.ui.components.location.CtsGeoLocation;
import cts.app.ui.components.map.CtsGoogleMap;
import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.Coordinate;

@UIScope
@SpringComponent
//@PreserveOnRefresh
@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

	private static final long serialVersionUID = -487630249371220520L;

	@Autowired
	private CtsGoogleMap map;
	
	@PostConstruct
	public void init() {
        setSpacing(false);

        Image img = new Image("./images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));
        DummyComps cmp = new DummyComps();
        cmp.setName("Modified w/ function");
        add(cmp);
        Button clear = new Button("Clear Map");
        clear.addClickListener(e -> map.removeMarkers());
        add(clear);
        Div mapDiv = new Div();
        mapDiv.setHeight("500px");
        mapDiv.setWidth("800px");
        mapDiv.add(map);
        map.addClickListener(event -> {
        	Coordinate c =  event.getIncomingCoord();
        	event.setOutputCoord(new Coordinate(0.D,0.D));
        	event.setOutputCoord(new Coordinate(1.D,1.D));
        });
        Div location = new Div();
        location.setWidth("800px");
        location.setHeight("250px");
        CtsGeoLocation loc = new CtsGeoLocation();
        location.add(loc);
        add(mapDiv);
        Button pos = new Button("Act Position");
        add(pos);
        add(location);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		//map.addMarker("Margit island", new Coordinate(47.517088, 19.043892));
	}

}
