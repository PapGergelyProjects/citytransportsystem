package prv.pgergely.cts.common.domain;

import java.time.LocalDate;

public class FeedLocationsJson {
	
	public Long id;
	public String title;
	public String dsUrl;
	public Feed feed;
	public double lat;
	public double lon;
	public boolean isEnabled;
	public boolean isActive;
	public String schemaName;
	
	public static class Feed{
		public String title;
		public LocalDate latest;
		@Override
		public String toString() {
			return "Feed {\ntitle:" + title + ", \nlatest:" + latest + "\n}";
		}
	}

	@Override
	public String toString() {
		return "FeedLocationsJson {\nid:" + id + ", \ntitle:" + title + ", \ndsUrl:" + dsUrl + ", \nfeed:" + feed
				+ ", \nlat:" + lat + ", \nlon:" + lon + ", \nisEnabled:" + isEnabled + ", \nisActive:" + isActive
				+ ", \nschemaName:" + schemaName + "\n}";
	}

	

}
