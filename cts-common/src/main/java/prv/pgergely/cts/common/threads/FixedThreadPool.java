package prv.pgergely.cts.common.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import prv.pgergely.cts.common.interfaces.FixedThreadEngine;

@Service
public class FixedThreadPool implements FixedThreadEngine{
	
	private ExecutorService cachedThreadPool;
	private ThreadManufactorum manufactorum;
	private Logger logger = LogManager.getLogger(FixedThreadPool.class);
	
	public FixedThreadPool(){
		manufactorum = new ThreadManufactorum(Thread.NORM_PRIORITY);
		cachedThreadPool = Executors.newCachedThreadPool(manufactorum);
		logger.info("Fixed Thread pool has been started");
	}
	
	@Override
	public void process(String name, Runnable process) {
		manufactorum.setThreadName(name);
		cachedThreadPool.execute(process);
		logger.info("Process "+name+" has been added to thread pool.");
	}

	@Override
	public void shutdown() {
		if(cachedThreadPool!=null){
			cachedThreadPool.shutdown();
			logger.debug("Thread pool has been shutdown");
		}
	}
	
	public ExecutorService getExecutor() {
		return cachedThreadPool;
	}
}
