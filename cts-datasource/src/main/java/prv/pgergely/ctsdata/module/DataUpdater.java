package prv.pgergely.ctsdata.module;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.config.CtsDataConfig;
import prv.pgergely.ctsdata.service.DataPreparation;
import prv.pgergely.ctsdata.service.TransitFeedPackageDownloader;

@Order(2)
@Component
public class DataUpdater implements ApplicationRunner {
	
	@Autowired
	private CtsDataConfig config;
	
	@Autowired
	private DataPreparation dataPrep;
	
	@Autowired
	private TransitFeedPackageDownloader zipContent;
	
	@Autowired
	private ScheduledThreadEngine thEngine;
	
	@Autowired
	private Queue<DownloadRequest> internalStore;
	
	private Logger logger = LogManager.getLogger(DataUpdater.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Runnable logic = () -> {
			logger.info("Data updater started");
			while(!internalStore.isEmpty()) {
				DownloadRequest zip = internalStore.poll();
				try {
					byte[] downloadedStream = downloadZipFile(zip.getUrlAddress(), zip.getFileName(), zip.getFeedId());
					dataPrep.extractZipFile(downloadedStream);
				} catch (IOException | CannotGetJdbcConnectionException | SQLException e) {
					logger.error(e.getMessage());
				}
			}
		};
		thEngine.process(config.getThreadParams().getInitDelayed(), config.getThreadParams().getDelayBetween(), TimeUnit.SECONDS, "TF_UPDATE", logic);
	}
	
    private byte[] downloadZipFile(String urlAddress, String archiveName, long feedId) throws IOException{
    	logger.info("Download file from: "+urlAddress);
    	URI getZipUrl = zipContent.getLinkFromLocation(urlAddress);
    	String zipUrl = getZipUrl==null ? urlAddress : getZipUrl.toString();
    	ResponseEntity<byte[]> entity = zipContent.getZipFile(zipUrl);
    	byte[] zipFile = entity.getBody();
//    	String fileName = entity.getHeaders().get("X-Alternate-FileName").get(0);
//    	String uri = fileName.isEmpty() ? archiveName : fileName;
//    	TransitFeedZipFile actZipFile = new TransitFeedZipFile(feedId, uri, zipFile);
    	logger.info("Download finished!");
        
        return zipFile;
    }

}
