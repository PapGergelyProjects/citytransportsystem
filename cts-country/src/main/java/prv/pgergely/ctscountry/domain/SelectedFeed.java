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
	public LocalDate latest;

	@Override
	public String toString() {
		return "SelectedFeed [id=" + id + ", title=" + title + ", latest=" + latest + "]";
	}
	
}
