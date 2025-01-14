package cts.app.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import prv.pgergely.cts.common.domain.DataSourceState;

public class GtfsFeedView implements Serializable {
	
	private static final long serialVersionUID = -1661190667055352079L;
	
	private Long id;
	private String title;
	private String countryCode;
	private boolean isEnabled;
	private String feedTitle;
	private OffsetDateTime latest;
	private boolean isActive;
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
	public OffsetDateTime getLatest() {
		return latest;
	}
	public void setLatest(OffsetDateTime latest) {
		this.latest = latest;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public DataSourceState getState() {
		return state;
	}
	public void setState(DataSourceState state) {
		this.state = state;
	}
}
