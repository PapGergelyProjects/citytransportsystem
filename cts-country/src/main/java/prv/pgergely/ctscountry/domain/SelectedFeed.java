package prv.pgergely.ctscountry.domain;

/**
 * 
 * @author Pap Gergely
 *
 */
public class SelectedFeed{
	
	public long id;
	public String title;
	public long latest;

	@Override
	public String toString() {
		return "SelectedFeed [id=" + id + ", title=" + title + ", latest=" + latest + "]";
	}
	
}
