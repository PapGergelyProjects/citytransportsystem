package cts.app.ui.utils;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Component
public class CtsNotification {
	
	public void showNotification(NotificationVariant variant, String... texts) {
		Notification noty = new Notification();
		noty.setThemeName(variant.getVariantName());
		noty.setPosition(Position.TOP_CENTER);
		noty.setDuration(2000);
		VerticalLayout root = new VerticalLayout();
		for(String text : texts) {
			root.add(new Span(text));
		}
		noty.add(root);
		noty.open();
	}
	
}
