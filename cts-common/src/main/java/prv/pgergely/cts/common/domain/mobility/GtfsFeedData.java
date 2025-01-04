package prv.pgergely.cts.common.domain.mobility;

import java.io.Serializable;

import prv.pgergely.cts.common.domain.CommonFeedData;

public class GtfsFeedData extends CommonFeedData implements Serializable {
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GtfsFeedData {\n    id:" + id + ", \n    getTitle():" + getTitle() + ", \n    getFeedTitle():"
				+ getFeedTitle() + ", \n    getLatestVersion():" + getLatestVersion() + ", \n    getDsUrl():"
				+ getDsUrl() + ", \n    isEnabled():" + isEnabled() + ", \n    isActive():" + isActive()
				+ ", \n    getSchemaName():" + getSchemaName() + ", \n    getState():" + getState() + "\n}";
	}
}
