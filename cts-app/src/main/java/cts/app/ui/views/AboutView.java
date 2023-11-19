package cts.app.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import cts.app.config.CtsConfig;
import cts.app.ui.MainLayout;
import cts.app.ui.components.DummyComps;
import cts.app.ui.components.map.CtsMap;
import cts.app.ui.components.map.InitMapData;
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
	private CtsConfig config;
	
	private CtsMap map;
	
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
        map = new CtsMap();
        map.initMap(new InitMapData("Center", new Coordinate(47.497912, 19.040235), 11, config.getGoogleApiKey()));
        clear.addClickListener(e -> map.removeMarkers());
        add(clear);
        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		map.addMarker("Margit island", new Coordinate(47.517088, 19.043892));
	}
	
	
}
