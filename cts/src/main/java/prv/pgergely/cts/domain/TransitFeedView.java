package prv.pgergely.cts.domain;

import java.time.LocalDate;

public class TransitFeedView {
	
	private Long id;
	private String title;
	private boolean isEnabled;
	private String feedTitle;
	private LocalDate latest;
	
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
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	public LocalDate getLatest() {
		return latest;
	}
	public void setLatest(LocalDate latest) {
		this.latest = latest;
	}
}
