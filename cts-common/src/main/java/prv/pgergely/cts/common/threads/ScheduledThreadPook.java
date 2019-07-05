package prv.pgergely.cts.common.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;

@Service
public class ScheduledThreadPook implements ScheduledThreadEngine {
	
	private ScheduledExecutorService scheduledService;
	private ThreadManufactorum manufactorum;
	
	private Logger logger = LogManager.getLogger(ScheduledThreadPook.class);
	
	public ScheduledThreadPook() {
		manufactorum = new ThreadManufactorum(Thread.NORM_PRIORITY);
		scheduledService = Executors.newScheduledThreadPool(3, manufactorum);
		logger.info("Scheduled Thread pool has been started");
	}

	@Override
	public void process(long delayed, long timeQty, TimeUnit unit, Runnable... processes) {

	}

	@Override
	public void process(long delayed, long timeQty, TimeUnit unit, String name, Runnable process) {
		manufactorum.setThreadName(name);
		scheduledService.scheduleAtFixedRate(process, delayed, timeQty, unit);
		logger.info("Thread "+name+" has been started.");
	}

	@Override
	public void shutdown() {
		if(scheduledService!=null){
			scheduledService.shutdown();
			logger.debug("Thread pool has been shutdown");
		}
	}

}
