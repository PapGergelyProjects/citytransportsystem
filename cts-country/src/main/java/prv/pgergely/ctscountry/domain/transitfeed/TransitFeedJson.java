package prv.pgergely.ctscountry.domain.transitfeed;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 GetFeedsResponse {
status (string, optional): Indicates the success status of this request. The following values are possible:
OK - Request was valid.
EMPTYKEY - Request was missing API key.
MISSINGINPUT - A required request parameter was missing.
INVALIDINPUT - A request parameter was invalid.
= ['OK', 'EMPTYKEY', 'MISSINGINPUT', 'INVALIDINPUT']stringEnum:"OK", "EMPTYKEY", "MISSINGINPUT", "INVALIDINPUT",
	ts (integer, optional): Indicates the timestamp (in number of seconds since the epoch (January 1 1970 00:00:00 GMT). ,
	msg (string, optional): Description of the error, if the status value was not OK. ,
	results (inline_model_0, optional): Contains requested data for a valid request.
}inline_model_0 {
	input (string, optional): If the status value is MISSINGINPUT or INVALIDINPUT, this field contains the name of the offending field. ,
	total (integer, optional): The total number of feeds found based on the request input. Note that this number may be larger than the number of feeds returned in feeds, based on the values for limit and page. ,
	limit (integer, optional): The maximum number of feeds that can be returned in this response. If the final page is being requested then this number may be larger than the number of feeds returned in feeds. integerDefault:10,
	page (integer, optional): The page number being requested, based on the maximum number than can be returned from in limit. ,
	numPages (integer, optional): The number of pages available, based on the total and limit. ,
	feeds (Array[Feed], optional): An array of zero or more feeds.
}Feed {
	id (string): The unique ID for this feed. This is constructed using the ID of the feed's provider and an internal ID. This ID can be used in other calls, such as /getFeedVersions or /getLatestFeedVersion. ,
	ty (string): The type of feed (such as GTFS or GTFS-realtime). = ['gtfs', 'gtfsrealtime']stringEnum:"gtfs", "gtfsrealtime",
	t (string): The title of the feed as it appears on TransitFeeds.com ,
	l (Location): The location to which this feed is assigned. The location ID can subsequently be used in calls to /getFeeds. ,
	u (inline_model_3, optional): Contains URLs with additional information about this feed. ,
	latest (inline_model_4, optional): If available, contains information about the latest version of this feed. This can help to determine if you need to call /getLatestFeedVersion to retrieve a newer version of a feed.
}Location {
	id (integer): The unique ID for this location. ,
	pid (integer): The ID for the parent location. If a location has no parent this value is 0. ,
	t (string): The title of this location. This may include state/province and country, depending on the location of type it refers to. ,
	n (string): The title of this location on its own (i.e. without any state or country information). ,
	lat (number): The latitude of the approximate point of this location. ,
	lng (number): The longitude of the approximate point of this location.
}inline_model_3 {
	i (string, optional): If available, this contains a URL with additional information about registering or downloading the feed from the provider. ,
	d (string, optional): If available, this contains a URL to download the feed directly from the provider (as opposed to downloading from TransitFeeds.com).
}inline_model_4 {
	ts (integer, optional): Indicates the timestamp of the latest feed version (in number of seconds since the epoch (January 1 1970 00:00:00 GMT).
}
 */


/**
 * This class represents the getFeed json.
 * Every property is public, because no need to instantiate, this is only for gson parser.
 * 
 * @author Pap Gergely
 *
 */
@Deprecated
public class TransitFeedJson implements Serializable {

    public String status;
    public long ts;
    public Results results;

    public static class Results{
        public int total;
        public int limit;
        public int page;
        public int numPages;
        public Feeds[] feeds;

        @Override
        public String toString() {
            return "{" + "total:" + total + ", limit:" + limit + ", page:" + page + ", numPages:" + numPages + ", feeds:" + feeds + '}';
        }
    }
    
    public static class Feeds{
        public String id;
        
        @JsonProperty("ty")
        public String typeOfFeed;
        
        @JsonProperty("t")
        public String feedTitle;
        
        @JsonProperty("l")
        public Location location;
        
        @JsonProperty("u")
        public FeedURL feedUrl;
        
        public Latest latest;

		@Override
		public String toString() {
			return "{id=" + id + ", typeOfFeed=" + typeOfFeed + ", feedTitle=" + feedTitle + ", location="+ location + ", feedUrl=" + feedUrl + ", latest=" + latest + "}";
		}
    }

    public static class Location{
        public long id;
        public long pid;
        
        @JsonProperty("t")
        public String rawLocationTitle;
        
        @JsonProperty("n")
        public String locationTitle;
        
        public double lat;
        public double lng;
		
        @Override
		public String toString() {
			return "{id=" + id + ", pid=" + pid + ", rawLocationTitle=" + rawLocationTitle + ", locationTitle="+ locationTitle + ", lat=" + lat + ", lng=" + lng + "}";
		}
    }

    public static class FeedURL{
    	
    	@JsonProperty("i")
    	public String urlInfo;
    	
    	@JsonProperty("d")
    	public String urlDirectLink;

		@Override
		public String toString() {
			return "FeedURL [urlInfo=" + urlInfo + ", urlDirectLink=" + urlDirectLink + "]";
		}
    }

    public static class Latest{
    	
    	@JsonProperty("ts")
        public long timestamp;

        @Override
        public String toString() {
            return "{" + "timestamp=" + timestamp + '}';
        }
        
    }

    @Override
    public String toString() {
        return "SwaggerFeed{" + "status=" + status + ", ts=" + ts + ", results=" + results + '}';
    }
}




