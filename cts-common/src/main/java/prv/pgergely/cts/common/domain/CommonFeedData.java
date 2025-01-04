package prv.pgergely.cts.common.domain;

import java.time.LocalDate;

public class CommonFeedData {

	private String title;
	private String feedTitle;
	private LocalDate latestVersion;
	private String dsUrl;
	private boolean isEnabled;
	private boolean isActive;
	private String schemaName;
	private DataSourceState state;
	
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
	public LocalDate getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(LocalDate latestVersion) {
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
		return "CommonFeedData {\n    title:" + title + ", \n    feedTitle:" + feedTitle + ", \n    latestVersion:"
				+ latestVersion + ", \n    dsUrl:" + dsUrl + ", \n    isEnabled:" + isEnabled + ", \n    isActive:"
				+ isActive + ", \n    schemaName:" + schemaName + ", \n    state:" + state + "\n}";
	}
	
}
