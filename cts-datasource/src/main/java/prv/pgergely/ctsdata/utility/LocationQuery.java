package prv.pgergely.ctsdata.utility;

import java.util.EnumSet;

public enum LocationQuery {
	
	WITH_TIMES("locations_with_times"),
	ONLY_LOCATIONS("locations"),
	INVALID("null");
	
	private String method;
	private static EnumSet<LocationQuery> all = EnumSet.allOf(LocationQuery.class);
	
	private LocationQuery(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public static LocationQuery getByMethod(String method) {
		return all.stream().filter(p -> p.getMethod().equals(method)).findFirst().orElse(INVALID);
	}
}
