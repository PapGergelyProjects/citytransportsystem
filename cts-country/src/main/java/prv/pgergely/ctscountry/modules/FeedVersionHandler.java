package prv.pgergely.ctscountry.modules;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.commsystem.HttpCommSystem;
import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.configurations.TransitFeedsTemplate;
import prv.pgergely.ctscountry.domain.TransitFeedJson.FeedURL;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Latest;
import prv.pgergely.ctscountry.domain.TransitFeedJson.Location;
import prv.pgergely.ctscountry.interfaces.VersionHandlerThread;
import prv.pgergely.ctscountry.model.FeedVersion;
import prv.pgergely.ctscountry.services.FeedSource;
import prv.pgergely.ctscountry.services.FeedVersionServiceImpl;

@Component
public class FeedVersionHandler implements VersionHandlerThread {
	
	@Autowired
	private CtsConfig config;
	
	@Autowired
	private HttpCommSystem http;
	
	@Autowired
	private FeedVersionServiceImpl feedVsSrv;
	
	@Autowired
	private FeedSource feed;
	
	@Autowired
	private TransitFeedZipFileContent zipContent;
	
	private Logger logger = LogManager.getLogger(FeedVersionHandler.class);
	
	@Override
	public void run() {
		logger.info("Starting version handler process...");
		try {
			Map<String, FeedVersion> links = checkSelectedFeed();
			for (Map.Entry<String, FeedVersion> pair : links.entrySet()) {
				String[] fileUrl = pair.getKey().split("/");
				downloadFile(pair.getKey(),fileUrl[fileUrl.length-1]);
				FeedVersion vers = pair.getValue();
				vers.setNewVersion(true);
				feedVsSrv.update(vers);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, FeedVersion> checkSelectedFeed() throws IOException{
		Map<String, FeedVersion> downloadLinks = new HashMap<>();
		List<FeedVersion> feedVers = feedVsSrv.getFeedVersions();
		logger.info("Check feed versions...");
		for (FeedVersion feedVersion : feedVers) {
			Feeds allFeed = feed.getFeed(feedVersion.getFeedId());
			Location location = allFeed.l;
			FeedURL feedLink = allFeed.u;
			Latest latest = allFeed.latest;
			LocalDate verDate = Instant.ofEpochMilli(latest.ts*1000).atZone(ZoneId.systemDefault()).toLocalDate();
			if(feedVersion.getFeedId() == allFeed.l.id && (feedVersion.getLatestVersion().isBefore(verDate) || feedVersion.isRecent())){
				long feedId = location.id;
				String title = allFeed.t;
				String link = feedLink.d;
				downloadLinks.put(link, feedVersion);
				
				FeedVersion newVersion = new FeedVersion();
				newVersion.setId(feedVersion.getId());
				newVersion.setFeedId(feedId);
				newVersion.setTitle(title);
				newVersion.setLatestVersion(verDate);
				newVersion.setRecent(false);
				feedVsSrv.update(newVersion);
				logger.info("New version founded.");
			}
		}
		return downloadLinks;
	}
	
    private void downloadFile(String urlAddress, String archiveName) throws IOException{
    	ResponseEntity<byte[]> entity = zipContent.getZipFile(urlAddress); 
    	byte[] zipFile = entity.getBody();
        logger.info("Download file from: "+urlAddress);
        try(InputStream in = new ByteArrayInputStream(zipFile)){
        	String fileName = entity.getHeaders().get("X-Alternate-FileName").get(0);
        	String uri = config.getTempDirectory()+"/"+(fileName.isEmpty() ? archiveName : fileName);
            Files.copy(in, Paths.get(uri+".tmp"), StandardCopyOption.REPLACE_EXISTING);
            renameFile(uri+".tmp", uri);
        }catch(MalformedURLException m){
        	logger.error(m);
        }
        logger.info("Download finished!");
    }
    
    private void renameFile(String path, String newName) throws IOException{
    	Path source = Paths.get(path);
    	Files.move(source, source.resolveSibling(newName), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
    }

}
