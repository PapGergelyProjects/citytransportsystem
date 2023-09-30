package prv.pgergely.ctsdata.utility;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import prv.pgergely.cts.common.domain.SourceState;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter implements WebSocketConfigurer {
	
	private Logger logger = LogManager.getLogger(WebSocketSessionHandler.class);
	private static final AtomicReference<StompSession> SESSION = new AtomicReference<>(); 
	private AtomicBoolean reconnecting = new AtomicBoolean();
	
	private WebSocketStompClient stompClient;
	private String websocketUrl;
	
	public WebSocketSessionHandler(WebSocketStompClient stompClient, String websocketUrl) {
		this.stompClient = stompClient;
		this.websocketUrl = websocketUrl;
	}

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
		SESSION.set(session);
		logger.info("subscribe to...");
		session.send("/app/refreshing", new SourceState(-1L, "Client", "CheckAlive"));
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		logger.error("WS excp: "+command+" "+headers, exception);
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		logger.warn("Connection lost for :"+session.getSessionId());
		if(!session.isConnected()) {
			if (reconnecting.compareAndSet(false, true)) {  //Ensures that only one thread tries to reconnect
				try {
					reconnect();
				} finally {
					reconnecting.set(false);
				}
			}
		} else {
			logger.error("WS excp: "+session.getSessionId(), exception);
		}
//		if(exception instanceof ConnectionLostException closed) {
//		}
	}
	
	private void reconnect() {
		boolean isDiscon = true;
		while(isDiscon) {
			try {
				TimeUnit.SECONDS.sleep(10);
				stompClient.connectAsync(websocketUrl, this).get();
				isDiscon = false;
				logger.warn("Successful reconnection.");
			} catch (Exception e) {
				logger.warn("Reconnect failed");
			}
		}
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new TextWebSocketHandler(), "/broadcast");
	}

	public static StompSession getSession() {
		return SESSION.get();
	}
	
	public static void publish(SourceState message) {
		getSession().send("/app/refreshing", message);
	}
}
