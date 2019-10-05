package prv.pgergely.ctsdata.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
			List<File> files = dataPrep.checkFolderForContent();
			try {
				if(!files.isEmpty()){
					logger.info("Zip file in the folder.");
					dataPrep.clearTables();
					logger.info("Tables Cleard");
					for(File file : files) {
						dataPrep.extractZipFile(file.getPath());
						dataPrep.createInsertFromFile();
						Files.delete(Paths.get(file.getPath()));
					}
				}else {
					logger.info("File was not found in the folder.");
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		};
		thEngine.process(config.getThreadParams().getInitDelayed(), config.getThreadParams().getDelayBetween(), TimeUnit.SECONDS, "data_updater", logic);
	}

}
