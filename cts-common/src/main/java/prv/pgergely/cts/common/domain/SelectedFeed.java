package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * @author Pap Gergely
 *
 */
public class SelectedFeed implements Serializable{
	
	private Long id;
	private String title;
	private String technicalTitle;
	private DataSourceState state;
	private LocalDate latest;
	
	public SelectedFeed() {}
	
	public SelectedFeed(Long id, String title, String technicalTitle, DataSourceState state, LocalDate latest) {
		this.id = id;
		this.title = title;
		this.technicalTitle = technicalTitle;
		this.state = state;
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
	
	public DataSourceState getState() {
		return state;
	}

	public void setState(DataSourceState state) {
		this.state = state;
	}

	public LocalDate getLatest() {
		return latest;
	}

	public void setLatest(LocalDate latest) {
		this.latest = latest;
	}

	@Override
	public String toString() {
		return "SelectedFeed {\nid:" + id + ", \ntitle:" + title + ", \ntechnicalTitle:" + technicalTitle + ", \nstate:"
				+ state + ", \nlatest:" + latest + "\n}";
	}
	
}
