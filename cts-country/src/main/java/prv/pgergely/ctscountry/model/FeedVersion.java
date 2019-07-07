package prv.pgergely.ctscountry.model;

import java.io.Serializable;
import java.time.LocalDate;

public class FeedVersion implements Serializable{
	
	private long id;
	private long feedId;
	private String title;
	private LocalDate latestVersion;
	private boolean recent;
	private boolean newVersion;
	
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
	
	@Override
	public String toString() {
		return "FeedVersion [feedId=" + feedId + ", title=" + title + ", latestVersion=" + latestVersion + ", recent="
				+ recent + ", newVersion=" + newVersion + "]";
	}
}
