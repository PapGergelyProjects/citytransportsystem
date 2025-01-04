package prv.pgergely.cts.common.domain.transitfeed;

import java.io.Serializable;

import prv.pgergely.cts.common.domain.CommonFeedData;

public class FeedLocationsJson extends CommonFeedData implements Serializable {
	
	private Long id;
	private double lat;
	private double lon;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "FeedLocationsJson {\n    id:" + id + ", \n    lat:" + lat + ", \n    lon:" + lon + ", \n    getTitle():"
				+ getTitle() + ", \n    getFeedTitle():" + getFeedTitle() + ", \n    getLatestVersion():"
				+ getLatestVersion() + ", \n    getDsUrl():" + getDsUrl() + ", \n    isEnabled():" + isEnabled()
				+ ", \n    isActive():" + isActive() + ", \n    getSchemaName():" + getSchemaName()
				+ ", \n    getState():" + getState() + "\n}";
	}
	
}
