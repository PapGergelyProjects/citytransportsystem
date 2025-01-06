package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CommonFeedData implements Serializable {

	private Long id;
	private String title;
	private String feedTitle;
	private OffsetDateTime latestVersion;
	private String dsUrl;
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
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	public OffsetDateTime getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(OffsetDateTime latestVersion) {
		this.latestVersion = latestVersion;
	}
	public String getDsUrl() {
		return dsUrl;
	}
	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
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
		return "CommonFeedData {\n    id:" + id + ", \n    title:" + title + ", \n    feedTitle:" + feedTitle
				+ ", \n    latestVersion:" + latestVersion + ", \n    dsUrl:" + dsUrl + ", \n    isEnabled:" + isEnabled
				+ ", \n    isActive:" + isActive + ", \n    schemaName:" + schemaName + ", \n    state:" + state
				+ "\n}";
	}
}
