package cts.app.ui.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.shared.Registration;

@Tag("test-comp")
@JsModule("./src/test-comp.ts")
public class DummyComps extends Component {

	public DummyComps() {
		this.getElement().setProperty("name", "My first lit comp");
	}
	
	public void setName(String name) {
		this.getElement().callJsFunction("setName", name);
	}
	
	@ClientCallable
	private void displayNoti(String text) {
		Notification.show(text);
	}
	
	public Registration addTitleClick(ComponentEventListener<TestCompClickEvent> lst) {
		return this.addListener(TestCompClickEvent.class, lst);
	}
}

@DomEvent("")
class TestCompClickEvent extends ComponentEvent<DummyComps>{

	public TestCompClickEvent(DummyComps source, boolean fromClient) {
		super(source, fromClient);
	}
	
}