package prv.pgergely.ctsdata.config;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketClient;
import io.vertx.core.http.WebSocketClientOptions;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.observable.ObservableObject;

@Component
public class WebsocketClientVerticle extends AbstractVerticle {
	
	@Autowired
	private Vertx vertx;
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private ObservableObject<SourceState> observed;
	
	private Logger logger = LogManager.getLogger(WebsocketClientVerticle.class);
	
	@Override
	public void start() throws Exception {
		startClient();
	}
	
	private void startClient() {
		WebSocketClientOptions opts = new WebSocketClientOptions();
		WebSocketClient client = vertx.createWebSocketClient(opts);
		client.connect(743, "localhost", "/cts-app/channel", handler -> {
			if(handler.failed()) {
				logger.error(handler.cause().getMessage(), handler.cause());
				retry(client);
			} else {
				final WebSocket socket = handler.result();
				socket.handler(data -> {
					SourceState stateFromServer = data.toJsonObject().mapTo(SourceState.class);
					logger.info("From server: "+stateFromServer.toString());
				}).exceptionHandler(excp -> {
					logger.error(excp.getMessage(), excp);
				}).closeHandler(cls -> {
					retry(client);
				});
				observed.subscribe("socket-client", event -> {
					socket.writeTextMessage(event.toJsonObj().toJson());
					logger.info("Message sent to server.");
				});
			}
		});
	}
	
	private void retry(WebSocketClient client) {
		logger.info("Retry connecting to the server: ");
		client.close();
		observed.unsubscribe("socket-client");
		vertx.setTimer(TimeUnit.SECONDS.toMillis(10), retry -> startClient());
	}

	@Override
	public void stop() throws Exception {
	}
	
}
