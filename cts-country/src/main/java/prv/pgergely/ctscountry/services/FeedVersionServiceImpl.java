package prv.pgergely.ctscountry.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.interfaces.FeedVersionRepo;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class FeedVersionServiceImpl {
	
	@Autowired
	private FeedVersionRepo feedVsDao;
	
	public void insert(FeedVersion value) {
		feedVsDao.insert(value);
	}

	public void update(FeedVersion value) {
		feedVsDao.update(value);
	}

	public FeedVersion getFeedVersionById(long id) {
		return feedVsDao.getFeedVersionById(id);
	}

	public List<FeedVersion> getFeedVersions() {
		return feedVsDao.getFeedVersions();
	}

	public void deleteFeedVersion(FeedVersion value) throws HttpClientErrorException {
		feedVsDao.deletegetFeedVersion(value);
	}

}
