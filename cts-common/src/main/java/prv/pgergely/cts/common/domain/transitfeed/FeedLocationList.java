package prv.pgergely.cts.common.domain.transitfeed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import prv.pgergely.cts.common.domain.transitfeed.FeedLocationsJson;

public class FeedLocationList implements Serializable {
	
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
