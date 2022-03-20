package prv.pgergely.ctscountry.model;

import java.io.Serializable;
import java.time.LocalDate;

import prv.pgergely.ctscountry.domain.SelectedFeed;

public class FeedVersion implements Serializable{

	private static final long serialVersionUID = 4910263032782960952L;
	
	private long id;
	private long feedId;
	private String title;
	private String technicalTitle;
	private LocalDate latestVersion;
	private boolean recent;
	private boolean newVersion;
	private boolean active = false;
	private String dsUrl = "";
	
	public FeedVersion() {}
	
	public FeedVersion(SelectedFeed feed, boolean isNewVersion) {
		this.feedId = feed.getId();
		this.title = feed.getTitle();
		this.technicalTitle = feed.getTechnicalTitle();
		this.latestVersion = feed.getLatest();
		this.recent = isNewVersion;
		this.newVersion = isNewVersion;
	}
	
	public FeedVersion(long feedId) {
		this.feedId = feedId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(LocalDate latestVersion) {
		this.latestVersion = latestVersion;
	}
	public boolean isRecent() {
		return recent;
	}
	public void setRecent(boolean recent) {
		this.recent = recent;
	}
	public boolean isNewVersion() {
		return newVersion;
	}
	public void setNewVersion(boolean newVersion) {
		this.newVersion = newVersion;
	}
	public String getTechnicalTitle() {
		return technicalTitle;
	}
	public void setTechnicalTitle(String technicalTitle) {
		this.technicalTitle = technicalTitle;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDsUrl() {
		return dsUrl;
	}
	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}

	@Override
	public String toString() {
		return "FeedVersion [id=" + id + ", feedId=" + feedId + ", title=" + title + ", technicalTitle="
				+ technicalTitle + ", latestVersion=" + latestVersion + ", recent=" + recent + ", newVersion="
				+ newVersion + ", active=" + active + ", dsUrl=" + dsUrl + "]";
	}
	
}
