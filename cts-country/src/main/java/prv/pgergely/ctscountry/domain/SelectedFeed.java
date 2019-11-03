package prv.pgergely.ctscountry.domain;

import java.time.LocalDate;

/**
 * 
 * @author Pap Gergely
 *
 */
public class SelectedFeed{
	
	public long id;
	public String title;
	public String technicalTitle;
	public LocalDate latest;
	
	@Override
	public String toString() {
		return "SelectedFeed [id=" + id + ", title=" + title + ", technicalTitle=" + technicalTitle + ", latest="
				+ latest + "]";
	}
	
}
