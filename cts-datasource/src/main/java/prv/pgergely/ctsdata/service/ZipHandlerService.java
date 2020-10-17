package prv.pgergely.ctsdata.service;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DownloadRequest;

@Component
public class ZipHandlerService {
	
	@Autowired
	private DataPreparation dataPrep;
	
	@Autowired
	private TransitFeedPackageDownloader zipContent;
	
	private Logger logger = LogManager.getLogger(ZipHandlerService.class);
	
	public void proceedZipFile(final DownloadRequest zip) {// TODO: save the download event...
		try {
			byte[] downloadedStream = downloadZipFile(zip.getUrlAddress(), zip.getFileName(), zip.getFeedId());
			dataPrep.extractZipFile(downloadedStream);
		} catch (IOException | CannotGetJdbcConnectionException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
    private byte[] downloadZipFile(String urlAddress, String archiveName, long feedId) throws IOException{
    	logger.info("Download file from: "+urlAddress);
    	URI getZipUrl = zipContent.getLinkFromLocation(urlAddress);
    	String zipUrl = getZipUrl==null ? urlAddress : getZipUrl.toString();
    	ResponseEntity<byte[]> entity = zipContent.getZipFile(zipUrl);
    	byte[] zipFile = entity.getBody();
    	logger.info("Download finished!");
        
        return zipFile;
    }

}
