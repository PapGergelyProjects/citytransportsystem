package cts.app.config.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereFramework.AtmosphereHandlerWrapper;
import org.springframework.stereotype.Component;

import com.vaadin.flow.server.RequestHandler;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.communication.PushRequestHandler;

//@WebServlet(value = "/*", asyncSupported = true)
//@Component
public class VaadinConfig implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		VaadinService srvc = event.getSource();
		srvc.addSessionInitListener(init -> {
			Iterable<RequestHandler> handlers = init.getSource().getRequestHandlers();
			for(RequestHandler handler : handlers) {
				if(handler instanceof PushRequestHandler push) {//AtmosphereFramework
					Field[] fields = push.getClass().getDeclaredFields();
					for(Field field : fields) {
						if(field.getType() == AtmosphereFramework.class) {
							field.setAccessible(true);
							try {
								AtmosphereFramework framework = (AtmosphereFramework)field.get(push);
								List<AtmosphereHandlerWrapper> wrappers = framework.getAtmosphereHandlers().values().stream().collect(Collectors.toList());
								for(AtmosphereHandlerWrapper wrapper : wrappers) {
									wrapper.interceptors.add(new VaadinWebSocketInterceptor());
								}
								Objects.hashCode("");
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		});
	}
	
}
