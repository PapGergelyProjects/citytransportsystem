package prv.pgergely.ctsdata.utility;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.SourceState;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter implements WebSocketConfigurer {
	
	private Logger logger = LogManager.getLogger(WebSocketSessionHandler.class);

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return SourceState.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		SourceState msg = (SourceState)payload;
		logger.info("Received: "+msg);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		logger.info("New session established : " + session.getSessionId());
		session.subscribe("/cts-channel/messaging", this);
		logger.info("subscribe to...");
		session.send("/app/refreshing", new SourceState(-1L, "Client", DataSourceState.TECHNICAL));
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		logger.error("WS excp: "+command+" "+headers, exception);
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		logger.warn("Connection lost for :"+session.getSessionId());
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new TextWebSocketHandler(), "/broadcast");
	}

}
