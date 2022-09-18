package prv.pgergely.ctsdata.module;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.utility.Schema;
import prv.pgergely.ctsdata.utility.WebSocketSessionHandler;

@Component
public class SocketRunner implements ApplicationRunner {
	
	@Autowired
	private Schema schema;
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		threadEng.process(1, 30, TimeUnit.SECONDS, "TestSocketUpdate", () -> {
			WebSocketSessionHandler.publish(new SourceState(schema.getFeedId(),"BKK_GTFS", "UPDATING"));
		});
		threadEng.process(15, 30, TimeUnit.SECONDS, "TestSocketOnline", () -> {
			WebSocketSessionHandler.getSession().send("/app/channel", new SourceState(schema.getFeedId(), "BKK_GTFS", "ONLINE"));
		});
		threadEng.process(20, 30, TimeUnit.SECONDS, "TestSocketOffline", () -> {
			WebSocketSessionHandler.getSession().send("/app/channel", new SourceState(schema.getFeedId(), "BKK_GTFS", "OFFLINE"));
		});
	}

}
