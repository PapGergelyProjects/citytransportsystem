package prv.pgergely.cts.common.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;

@Service
public class ScheduledThreadPool implements ScheduledThreadEngine {
	
	private ScheduledExecutorService scheduledService;
	private ThreadManufactorum manufactorum;
	
	private Logger logger = LogManager.getLogger(ScheduledThreadPool.class);
	
	public ScheduledThreadPool() {
		manufactorum = new ThreadManufactorum(Thread.NORM_PRIORITY);
		scheduledService = Executors.newScheduledThreadPool(3, manufactorum);
		logger.info("Scheduled Thread pool has been started");
	}

	@Override
	public void process(long delayed, long timeQty, TimeUnit unit, Runnable... processes) {
	}

	@Override
	public void process(long initDelayed, long delayBetween, TimeUnit unit, String name, Runnable process) {
		manufactorum.setThreadName(name);
		scheduledService.scheduleWithFixedDelay(process, initDelayed, delayBetween, unit);
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
