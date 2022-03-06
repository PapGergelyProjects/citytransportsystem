package prv.pgergely.cts.domain;

import java.time.LocalDate;

public class TransitFeed {
	
	public Long id;
	public String title;
	public Feed feed;
	public boolean isEnabled;
	
	public static class Feed{
		public String title;
		public LocalDate latest;
	}

	@Override
	public String toString() {
		return "TransitFeed={id:" + id + ", title:" + title + ", feed:" + feed + ", enabled:" + isEnabled + "}";
	}
	
}
