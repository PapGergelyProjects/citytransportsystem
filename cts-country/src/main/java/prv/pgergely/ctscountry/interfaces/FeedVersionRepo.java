package prv.pgergely.ctscountry.interfaces;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.model.FeedVersion;

public interface FeedVersionRepo {
	
	public void insert(FeedVersion value);
	public void update(FeedVersion value);
	public FeedVersion getFeedVersionById(long id);
	public List<FeedVersion> getFeedVersions();
	public void deletegetFeedVersion(FeedVersion value) throws HttpClientErrorException;
	
}
