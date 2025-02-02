package cts.app.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cts.app.config.WebsocketVerticle;
import io.vertx.core.Vertx;

@Component
public class AppRun implements ApplicationRunner {
	
	@Autowired
	private Vertx vertx;
	
	@Autowired
	private WebsocketVerticle websocketServer;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		vertx.deployVerticle(websocketServer);
	}

}
