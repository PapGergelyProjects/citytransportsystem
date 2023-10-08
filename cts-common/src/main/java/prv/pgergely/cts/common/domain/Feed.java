package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Feed implements Serializable{
	
	private String title;
	private LocalDate latest;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getLatest() {
		return latest;
	}

	public void setLatest(LocalDate latest) {
		this.latest = latest;
	}

	@Override
	public String toString() {
		return "Feed {\ntitle:" + title + ", \nlatest:" + latest + "\n}";
	}
}