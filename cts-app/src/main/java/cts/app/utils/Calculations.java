package cts.app.utils;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.toDegrees;

import java.util.LinkedList;
import java.util.List;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPoint;

/**
 * Contains calculations which are necessary for map visualization. 
 * 
 * @author PapGergely
 *
 */
public class Calculations {
	
	public static final double EARTH_RADIUS = 6378137.D;
	
	/**
	 * Calculates the arc points of a circle.
	 * 
	 * @param lat Center point's latitude
	 * @param lon Center point's longitude
	 * @param distanceInMeter The radius of the circle
	 * @return 
	 */
	public static List<GoogleMapPoint> calcArcPoints(double lat, double lon, int distanceInMeter) {
		double calcDistance = distanceInMeter / EARTH_RADIUS;
		double radianLat = toRadians(lat);
		double radianLon = toRadians(lon);
		List<GoogleMapPoint> arcPoints = new LinkedList<>();
		for(int i=0; i <= 360; i++) {
			double bear = toRadians(i);
			double calcLat = asin(sin(radianLat) * cos(calcDistance) + cos(radianLat) * sin(calcDistance) * cos(bear));
			double calcLon = radianLon + atan2(sin(bear) * sin(calcDistance) * cos(radianLat), cos(calcDistance) - sin(radianLat) * sin(calcLat));
			arcPoints.add(new GoogleMapPoint(toDegrees(calcLat), toDegrees(calcLon)));
		}
		
		return arcPoints;
	}
}
