package cts.app.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.observable.ObservableObject;

@Service
public class WebsocketVerticle extends AbstractVerticle {
	
	@Autowired
	private Vertx vertx;
	
	@Autowired
	private ObservableObject<SourceState> observed;
	
	private Logger logger = LogManager.getLogger(WebsocketVerticle.class);
	private Set<String> connectedClients = new HashSet<>();
	private Set<String> pausedClients = new HashSet<>();
	
	@Override
	public void start() throws Exception {
		HttpServerOptions opts = new HttpServerOptions();
		opts.setRegisterWebSocketWriteHandlers(true);
		HttpServer server = vertx.createHttpServer(opts);
		server.webSocketHandshakeHandler(hs -> {
			//Map<String, String> queryRes = parseQuery(hs.query());
			if(hs.path().equals("/cts-app/channel")) {
				hs.accept();
			} else {
				hs.reject(403);
				logger.error("Websocket connection rejected from "+hs.path(), new IllegalStateException(hs.path()+" is not permitted!"));
			}
		}).webSocketHandler((ctx) -> {
			connectedClients.add(ctx.textHandlerID());
			logger.info("Websocket client "+ctx.textHandlerID()+" connected to "+ctx.path());
			logger.info("Websocket server status: [connected: "+connectedClients.size()+", paused: "+pausedClients.size()+"]");
			System.out.println("VertX >>>> "+ctx.textHandlerID());
			ctx.textMessageHandler(msg -> {
				ObjectMapper mapper = new ObjectMapper();
				try {
					SourceState state = mapper.readValue(msg, SourceState.class);
					observed.next(state);
//					Boolean setPause = state.getState() == DataSourceState.UPDATING;
//					if(setPause) {
//						ctx.pause();
//						pausedClients.add(clientId);
//					} else {
//						ctx.resume();
//						pausedClients.remove(clientId);
//					}
					logger.info(state);
					ctx.writeTextMessage(new SourceState(-1L, "Server", DataSourceState.TECHNICAL).toJsonObj().toJson());
				} catch (JsonProcessingException e) {
					logger.error(e.getMessage(), e);
				}
			});
			ctx.closeHandler(close -> {
				logger.info("Websocket client "+ctx.textHandlerID()+" disconnected ");
				connectedClients.remove(ctx.textHandlerID());
			});
		}).listen(743, result -> {
		      if (result.succeeded()) {
		    	  logger.info("Websocket successfully started on port 743...");
		      } else {
		    	  logger.error(result.cause().getMessage(), result.cause());
		      }
		});
	}
	
	private Map<String, String> parseQuery(final String queryString) {
		Map<String, String> res = new HashMap<>();
		final String[] eachQuery = queryString.split("&");
		for(String query : eachQuery) {
			final String[] queryVal = query.split("=");
			res.put(queryVal[0], queryVal[1]);
		}
		
		return res;
	}
	
}
