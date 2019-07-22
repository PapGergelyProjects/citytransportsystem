package prv.pgergely.ctscountry.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.interfaces.FeedVersionDao;
import prv.pgergely.ctscountry.interfaces.FeedVersionService;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class FeedVersionServiceImpl implements FeedVersionService {
	
	@Autowired
	private FeedVersionDao feedVsDao;
	
	@Override
	public void insert(FeedVersion value) {
		feedVsDao.insert(value);
	}

	@Override
	public void update(FeedVersion value) {
		feedVsDao.update(value);
	}

	@Override
	public FeedVersion getFeedVersionById(long id) {
		return feedVsDao.getFeedVersionById(id);
	}

	@Override
	public List<FeedVersion> getFeedVersions() {
		return feedVsDao.getFeedVersions();
	}

	@Override
	public void deleteFeedVersion(FeedVersion value) {
		feedVsDao.deletegetFeedVersion(value);
	}

}
