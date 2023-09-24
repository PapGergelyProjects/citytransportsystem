package cts.app.config.util;

import java.util.List;
import java.util.Objects;

import org.atmosphere.config.AtmosphereHandlerConfig;
import org.atmosphere.cpr.Action;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereInterceptor;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereRequestImpl;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListener;
import org.atmosphere.cpr.AtmosphereResponse;

import prv.pgergely.cts.common.domain.SourceState;

public class VaadinWebSocketInterceptor implements AtmosphereInterceptor {

	@Override
	public void configure(AtmosphereConfig config) {
		List<AtmosphereHandlerConfig> conf = config.getAtmosphereHandlerConfig();
		Objects.hashCode("");
	}

	@Override
	public Action inspect(AtmosphereResource r) {
		r.addEventListener(new AtmosphereResourceEventListener() {

			@Override
			public void onHeartbeat(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onPreSuspend(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onSuspend(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onResume(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onDisconnect(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onBroadcast(AtmosphereResourceEvent event) {
				Object msg = event.getMessage();
				if(msg instanceof SourceState e) {
					Objects.hashCode("");
				}
				Objects.hashCode("");
			}

			@Override
			public void onThrowable(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}

			@Override
			public void onClose(AtmosphereResourceEvent event) {
				Objects.hashCode("");
			}
			
		});
		AtmosphereRequest req = r.getRequest();
		AtmosphereRequestImpl.Body body = req.body();
		AtmosphereResponse resp = r.getResponse();

		return Action.CONTINUE;
	}

	@Override
	public void postInspect(AtmosphereResource r) {

	}

	@Override
	public void destroy() {

	}

}
