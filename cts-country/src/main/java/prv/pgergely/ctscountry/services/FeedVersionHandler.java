package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.FeedURL;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.Feeds;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.Latest;
import prv.pgergely.ctscountry.domain.transitfeed.TransitFeedJson.Location;
import prv.pgergely.ctscountry.interfaces.VersionHandlerThread;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
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
			LocalDateTime verDate = Instant.ofEpochMilli(latest.timestamp*1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
			OffsetDateTime versionDateTime = OffsetDateTime.of(verDate, ZoneOffset.UTC); 
			if(feedVersion.getFeedId() == allFeed.location.id && (feedVersion.getLatestVersion().isBefore(versionDateTime) || feedVersion.isRecent())){ // FIXME: Handle new_version
				long feedId = location.id;
				String title = allFeed.feedTitle;
				String link = feedLink.urlDirectLink;
				downloadLinks.put(link, feedVersion);
				
				FeedVersion newVersion = new FeedVersion();
				newVersion.setId(feedVersion.getId());
				newVersion.setFeedId(feedId);
				newVersion.setTitle(title);
				newVersion.setLatestVersion(versionDateTime);
				newVersion.setRecent(false);
				feedVsSrv.update(newVersion);
				logger.info("New version founded.");
			}
		}
		return downloadLinks;
	}
}
