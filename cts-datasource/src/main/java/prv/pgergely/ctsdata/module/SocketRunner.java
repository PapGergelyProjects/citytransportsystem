package prv.pgergely.ctsdata.module;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.service.MessagePublisher;
import prv.pgergely.ctsdata.utility.Schema;

@Component
@Profile("test")
public class SocketRunner implements ApplicationRunner {
	
	@Autowired
	private Schema schema;
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@Autowired
	private MessagePublisher sender;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		threadEng.process(1, 30, TimeUnit.SECONDS, "TestSocketUpdate", () -> {
			DataSourceState state = DataSourceState.UPDATING;
			sender.publish(new SourceState(schema.getFeedId(),"BKK_GTFS", state));
		});
		threadEng.process(15, 30, TimeUnit.SECONDS, "TestSocketOnline", () -> {
			DataSourceState state = DataSourceState.ONLINE;
			sender.publish(new SourceState(schema.getFeedId(),"BKK_GTFS", state));
		});
		threadEng.process(20, 30, TimeUnit.SECONDS, "TestSocketOffline", () -> {
			DataSourceState state = DataSourceState.OFFLINE;
			sender.publish(new SourceState(schema.getFeedId(),"BKK_GTFS", state));
		});
	}

}
