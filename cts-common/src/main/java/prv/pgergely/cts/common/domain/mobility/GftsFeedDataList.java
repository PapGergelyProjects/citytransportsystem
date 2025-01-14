package prv.pgergely.cts.common.domain.mobility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GftsFeedDataList implements Serializable {
	
	private List<GtfsFeedData> data = new ArrayList<>();
	
	public GftsFeedDataList(List<GtfsFeedData> data) {
		this.data.addAll(data);
	}

	public List<GtfsFeedData> getData() {
		return data;
	}
	
}
