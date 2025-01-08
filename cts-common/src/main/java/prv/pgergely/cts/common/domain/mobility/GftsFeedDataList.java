package prv.pgergely.cts.common.domain.mobility;

import java.util.ArrayList;
import java.util.List;

public class GftsFeedDataList {
	
	private List<GtfsFeedData> data = new ArrayList<>();
	
	public GftsFeedDataList(List<GtfsFeedData> data) {
		this.data.addAll(data);
	}

	public List<GtfsFeedData> getData() {
		return data;
	}
	
}
