package prv.pgergely.cts.ui.views;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import prv.pgergely.cts.ui.MainLayout;

@PageTitle("Landing Page")
@Route(value = "main", layout = MainLayout.class)
@Tag("landing-page-view")
//@JsModule("./views/landingpage/landing-page-view.ts")
public class LandingPage extends VerticalLayout {
	
    private TextField name;
    private Button sayHello;
	
	@PostConstruct
	public void init() {
		this.add(new H1("This is the landingPage"));
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });

        setMargin(true);
        //setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
	}

}
