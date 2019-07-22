package prv.pgergely.ctscountry.domain;

import java.time.LocalDate;

public class FeedLocationsJson {
	
	public Long id;
	public String title;
	public Feed feed;
	
	public static class Feed{
		public String title;
		public LocalDate latest;
	}
}
