package prv.pgergely.cts.ui.views;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.ui.MainLayout;

@UIScope
@SpringComponent
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	@PostConstruct
	public void init() {
		this.add("This is the config page!");
	}
	
}
