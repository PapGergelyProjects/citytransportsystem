package prv.pgergely.ctsdata.utility;

public class DataUtils {
	
	public static String refineColorCode(String colorCode) {
		if(colorCode.contains("#")) {
			return colorCode;
		}
		return "#"+colorCode;
	}
}
