package prv.pgergely.ctscountry.modules;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DownloadRequest;
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
	private FeedVersionServiceImpl feedVsSrv;
	
	@Autowired
	private FeedSource feed;
	
	@Autowired
	private Queue<DownloadRequest> store;
	
	private Logger logger = LogManager.getLogger(FeedVersionHandler.class);
	
	@Override
	public void run() {
		logger.info("Starting version handler process...");
		try {
			Map<String, FeedVersion> links = checkSelectedFeed();
			for (Map.Entry<String, FeedVersion> pair : links.entrySet()) {
				String[] fileUrl = pair.getKey().split("/");
				String fileName = fileUrl[fileUrl.length-1];
				DownloadRequest request = new DownloadRequest(pair.getValue().getFeedId(), fileName, pair.getKey());
				store.add(request);
			}
		} catch (Exception e) {
			logger.error(e);
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
			if(feedVersion.getFeedId() == allFeed.location.id && (feedVersion.getLatestVersion().isBefore(verDate) || feedVersion.isRecent())){ // FIXME: Handle new_version
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
}
