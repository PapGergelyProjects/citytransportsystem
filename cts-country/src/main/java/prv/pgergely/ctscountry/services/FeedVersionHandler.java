package prv.pgergely.ctscountry.services;

import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctscountry.domain.mobility.feeds.SourceInfo;
import prv.pgergely.ctscountry.domain.mobility.gtfs.LatestDataset;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.model.FeedVersion;
import prv.pgergely.ctscountry.utils.CountryUtils;

@Service
public class FeedVersionHandler implements Runnable {
	
	@Autowired
	private FeedVersionServiceImpl feedVsSrv;
	
	@Autowired
	private MobilityApi api;
	
	@Autowired
	private Queue<DownloadRequest> store;
	
	private Logger logger = LogManager.getLogger(FeedVersionHandler.class);
	
	@Override
	public void run() {
		logger.info("Starting version handler process...");
		try {
			Map<URI, FeedVersion> links = checkSelectedFeed();
			for (Map.Entry<URI, FeedVersion> pair : links.entrySet()) {
				URI downLoadUri = pair.getKey();
				String[] fileUrl = downLoadUri.getPath().split("/");
				String fileName = fileUrl[fileUrl.length-1];
				DownloadRequest request = new DownloadRequest(pair.getValue().getFeedId(), fileName, downLoadUri);
				store.add(request);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private Map<URI, FeedVersion> checkSelectedFeed() throws IOException{
		Map<URI, FeedVersion> downloadLinks = new HashMap<>();
		List<FeedVersion> feedVers = feedVsSrv.getFeedVersions();
		logger.info("Check feed versions...");
		for (FeedVersion feedVersion : feedVers) {
			final String feedId = CountryUtils.convertRawIdToApiId(feedVersion.getFeedId());
			final MobilityGtfsFeed feed = api.getGtfsFeed(feedId);
			final LatestDataset data = feed.getLatestData();
			final SourceInfo source = feed.getSourceInfo();
			final OffsetDateTime versionDateTime = OffsetDateTime.parse(data.getDownloadAt()); 
			if(feedVersion.getLatestVersion().isBefore(versionDateTime) || feedVersion.isRecent()){ // FIXME: Handle new_version
				downloadLinks.put(URI.create(source.getProducerUrl()), feedVersion);
				feedVersion.setLatestVersion(versionDateTime);
				feedVersion.setRecent(false);
				feedVsSrv.update(feedVersion);
				logger.info("New version founded.");
			}
		}
		return downloadLinks;
	}
}
