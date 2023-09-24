package cts.app.domain;

import java.time.LocalDate;

/**
 * 
 * @author Pap Gergely
 *
 */
public class SelectedFeed{
	
	private Long id;
	private String title;
	private String technicalTitle;
	private LocalDate latest;
	
	public SelectedFeed() {}

	public SelectedFeed(Long id, String title, String technicalTitle, LocalDate latest) {
		this.id = id;
		this.title = title;
		this.technicalTitle = technicalTitle;
		this.latest = latest;
	}

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

	public String getTechnicalTitle() {
		return technicalTitle;
	}

	public void setTechnicalTitle(String technicalTitle) {
		this.technicalTitle = technicalTitle;
	}

	public LocalDate getLatest() {
		return latest;
	}

	public void setLatest(LocalDate latest) {
		this.latest = latest;
	}

	@Override
	public String toString() {
		return "SelectedFeed [id=" + id + ", title=" + title + ", technicalTitle=" + technicalTitle + ", latest="
				+ latest + "]";
	}
	
}
