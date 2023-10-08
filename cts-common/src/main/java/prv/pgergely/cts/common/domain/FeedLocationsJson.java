package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class FeedLocationsJson implements Serializable {
	
	private Long id;
	private String title;
	private String dsUrl;
	private Feed feed;
	private double lat;
	private double lon;
	private boolean isEnabled;
	private boolean isActive;
	private String schemaName;
	private DataSourceState state;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDsUrl() {
		return dsUrl;
	}

	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public DataSourceState getState() {
		return state;
	}

	public void setState(DataSourceState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "FeedLocationsJson {\nid:" + id + ", \ntitle:" + title + ", \ndsUrl:" + dsUrl + ", \nfeed:" + feed
				+ ", \nlat:" + lat + ", \nlon:" + lon + ", \nisEnabled:" + isEnabled + ", \nisActive:" + isActive
				+ ", \nschemaName:" + schemaName + ", \nstate:" + state + "\n}";
	}
}
