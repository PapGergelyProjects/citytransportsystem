package prv.pgergely.ctsdata.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.vertx.core.Vertx;
import prv.pgergely.ctsdata.config.WebsocketClientVerticle;

@Order(2)
@Component
public class WebsocketClientInit implements ApplicationRunner {
	
	@Autowired
	private Vertx vertx;
	
	@Autowired
	private WebsocketClientVerticle client;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		vertx.deployVerticle(client);
	}

}
