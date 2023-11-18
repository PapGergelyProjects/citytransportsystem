package cts.app.ui.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.flow.component.AttachEvent;
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
import cts.app.ui.components.CtsGoogleMap;
import cts.app.ui.components.DummyComps;
import jakarta.annotation.PostConstruct;

@UIScope
@SpringComponent
//@PreserveOnRefresh
@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

	private static final long serialVersionUID = -487630249371220520L;
	
	@Autowired
	private CtsConfig config;
	
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
        map = new CtsGoogleMap(config.getGoogleApiKey());
        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		map.initMap();
	}
	
	
}
