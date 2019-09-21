package prv.pgergely.ctscountry.interfaces;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.model.FeedVersion;

public interface FeedVersionService {
	public void insert(FeedVersion value);
	public void update(FeedVersion value);
	public FeedVersion getFeedVersionById(long id);
	public List<FeedVersion> getFeedVersions();
	public void deleteFeedVersion(FeedVersion value) throws HttpClientErrorException;
}
