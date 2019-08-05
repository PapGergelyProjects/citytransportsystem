package prv.pgergely.ctscountry.modules;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
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
	private FeedVersionServiceImpl feedVsSrv;
	
	@Autowired
	private FeedSource feed;
	
	@Autowired
	private TransitFeedZipFileContent zipContent;
	
	@Autowired
	private HttpCommSystem comm;
	
	private Logger logger = LogManager.getLogger(FeedVersionHandler.class);
	
	private AtomicInteger counter = new AtomicInteger(0);
	
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
			Location location = allFeed.location;
			FeedURL feedLink = allFeed.feedUrl;
			Latest latest = allFeed.latest;
			LocalDate verDate = Instant.ofEpochMilli(latest.timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDate();
			if(feedVersion.getFeedId() == allFeed.location.id && (feedVersion.getLatestVersion().isBefore(verDate) || feedVersion.isRecent())){
				long feedId = location.id;
				String title = allFeed.feedTitle;
				String link = feedLink.urlDirectLink;
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
	
//    private void downloadFile(String urlAddress, String archiveName) throws IOException{
//    	HttpURLConnection conn = comm.establishConnection(urlAddress);
//    	System.out.println(conn.getHeaderFields());
//        logger.info("Download file from: "+urlAddress);
//        try(InputStream in = conn.getInputStream()){
//        	String fileName = getFileName(conn);
//        	String uri = config.getTempDirectory()+"/"+(fileName.isEmpty() ? archiveName : fileName);
//            Files.copy(in, Paths.get(uri+".tmp"), StandardCopyOption.REPLACE_EXISTING);
//            renameFile(uri+".tmp", uri);
//        }catch(MalformedURLException m){
//        	logger.error(m);
//        }
//        logger.info("Download finished!");
//    }
//    
//    
//    private String getFileName(HttpURLConnection conn){
//    	List<String> contents = conn.getHeaderFields().get("Content-Disposition");
//    	if(contents != null && !contents.isEmpty()){
//    		String dispos = contents.get(0);
//    		Matcher match = Pattern.compile("(?<filename>filename\\=\\\".*\\\")").matcher(dispos);
//    		while(match.find()){
//    			String[] fileWithExtension = match.group("filename").split("\\=");
//    			return fileWithExtension[1].replaceAll("\"", "");
//    		}
//    		
//    	}
//    	return "";
//    }
//    
//    private void renameFile(String path, String newName) throws IOException{
//    	Path source = Paths.get(path);
//    	Files.move(source, source.resolveSibling(newName), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
//    }
	
//	private void downloadFile(String urlAddress, String archiveName) throws MalformedURLException, IOException {
//		ReadableByteChannel readChannel = Channels.newChannel(new URL(urlAddress).openStream());
//		try(FileOutputStream fileOut = new FileOutputStream(config.getTempDirectory()+"/"+archiveName)){
//			fileOut.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
//		}
//	}
	
    private void downloadFile(String urlAddress, String archiveName) throws IOException{
    	URI getZipUrl = zipContent.getLinkFromHeader(urlAddress).getBody();
    	ResponseEntity<byte[]> entity = zipContent.getZipFile(getZipUrl.toString());
    	System.out.println(entity.getHeaders());
    	byte[] zipFile = entity.getBody();
        logger.info("Download file from: "+urlAddress);
        try(InputStream in = new ByteArrayInputStream(zipFile)){
        	String fileName = entity.getHeaders().get("X-Alternate-FileName").get(0);
        	String uri = config.getTempDirectory()+"/"+(fileName.isEmpty() ? archiveName : fileName)+counter.getAndIncrement();
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
