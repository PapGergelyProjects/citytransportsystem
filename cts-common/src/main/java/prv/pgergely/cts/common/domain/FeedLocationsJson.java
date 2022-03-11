package prv.pgergely.cts.common.domain;

import java.time.LocalDate;

public class FeedLocationsJson {
	
	public Long id;
	public String title;
	public Feed feed;
	public double lat;
	public double lon;
	public boolean isEnabled;
	
	public static class Feed{
		public String title;
		public LocalDate latest;
	}

	@Override
	public String toString() {
		return "FeedLocationsJson={id:" + id + ", title:" + title + ", feed:" + feed + ", enabled:" + isEnabled + "}";
	}
	
}
