package prv.pgergely.ctscountry.domain;

/*
GetLocationsResponse {
status (string, optional): Indicates the success status of this request. The following values are possible:
OK - Request was valid.
EMPTYKEY - Request was missing API key.
MISSINGINPUT - A required request parameter was missing.
INVALIDINPUT - A request parameter was invalid.
= ['OK', 'EMPTYKEY', 'MISSINGINPUT', 'INVALIDINPUT']stringEnum:"OK", "EMPTYKEY", "MISSINGINPUT", "INVALIDINPUT",
	ts (integer, optional): Indicates the timestamp (in number of seconds since the epoch (January 1 1970 00:00:00 GMT). ,
	msg (string, optional): Description of the error, if the status value was not OK. ,
	results (inline_model, optional): Contains requested data for a valid request.
}inline_model {
	input (string, optional): If the status value is MISSINGINPUT or INVALIDINPUT, this field contains the name of the offending field. ,
	locations (Array[Location], optional): An array of zero or more locations.
}Location {
	id (integer): The unique ID for this location. ,
	pid (integer): The ID for the parent location. If a location has no parent this value is 0. ,
	t (string): The title of this location. This may include state/province and country, depending on the location of type it refers to. ,
	n (string): The title of this location on its own (i.e. without any state or country information). ,
	lat (number): The latitude of the approximate point of this location. ,
	lng (number): The longitude of the approximate point of this location.
}
*/

/**
 * This class represents the getLocation json from swagger.
 * This similar to the SwaggerFeed.
 * 
 * @author Pap Gergely
 *
 */
public class TransitFeedLocationJson {
	
	public String status;
	public long ts;
	public Results results;
	
	public class Results{
		public Locations[] locations;
	}
	
	public class Locations{
		public long id;
		public long pid;
		public String t;
		public String n;
		public double lat;
		public double lng;
	}
}
