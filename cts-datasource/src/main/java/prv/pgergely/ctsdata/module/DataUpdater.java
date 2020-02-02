package prv.pgergely.ctsdata.module;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.config.CtsDataConfig;
import prv.pgergely.ctsdata.service.DataPreparation;

@Order(2)
@Component
public class DataUpdater implements ApplicationRunner {
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private DataPreparation dataPrep;
	
	@Autowired
	private ScheduledThreadEngine thEngine;
	
	@Autowired
	private Queue<TransitFeedZipFile> internalStore;
	
	private Logger logger = LogManager.getLogger(DataUpdater.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Runnable logic = () -> {
			logger.info("Data updater started");
			while(!internalStore.isEmpty()) {
				TransitFeedZipFile zip = internalStore.poll();
				try {
					dataPrep.extractZipFile(zip.getZipStream());
				} catch (IOException | CannotGetJdbcConnectionException | SQLException e) {
					logger.error(e.getMessage());
				}
			}
		};
		thEngine.process(config.getThreadParams().getInitDelayed(), config.getThreadParams().getDelayBetween(), TimeUnit.SECONDS, "data_updater", logic);
	}

}
