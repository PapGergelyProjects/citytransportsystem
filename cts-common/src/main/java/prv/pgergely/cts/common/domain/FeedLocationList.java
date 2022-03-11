package prv.pgergely.cts.common.domain;

import java.util.ArrayList;
import java.util.List;

import prv.pgergely.cts.common.domain.FeedLocationsJson;

public class FeedLocationList {
	
	private List<FeedLocationsJson> list = new ArrayList<>();
	
	public FeedLocationList() {
	}

	public FeedLocationList(List<FeedLocationsJson> list) {
		this.list.addAll(list);
	}

	public List<FeedLocationsJson> getFeeds() {
		return list;
	}
	
}
