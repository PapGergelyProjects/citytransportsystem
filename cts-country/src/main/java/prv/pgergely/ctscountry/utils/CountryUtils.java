package prv.pgergely.ctscountry.utils;

public class CountryUtils {
	
	public static String convertRawIdToApiId(Long rawId) {
		return "mdb-"+rawId;
	}
	
	public static Long convertApiIdToRawId(String apiId) {
		String rawIdStr = apiId.replace("mdb-", "");
		return Long.valueOf(rawIdStr);
	}
}
