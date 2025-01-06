package cts.app.utils;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static java.lang.Math.sqrt;

import java.util.LinkedList;
import java.util.List;

import prv.pgergely.cts.common.domain.Coordinate;

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
	 * @return List of coordinates which are represent a circle
	 */
	public static List<Coordinate> calcArcPoints(double lat, double lon, int distanceInMeter) {
		final double calcDistance = distanceInMeter / EARTH_RADIUS;
		final double radianLat = toRadians(lat);
		final double radianLon = toRadians(lon);
		List<Coordinate> arcPoints = new LinkedList<>();
		for(int i=0; i <= 360; i++) {
			final double bear = toRadians(i);
			final double calcLat = asin(sin(radianLat) * cos(calcDistance) + cos(radianLat) * sin(calcDistance) * cos(bear));
			final double calcLon = radianLon + atan2(sin(bear) * sin(calcDistance) * cos(radianLat), cos(calcDistance) - sin(radianLat) * sin(calcLat));
			arcPoints.add(new Coordinate(toDegrees(calcLat), toDegrees(calcLon)));
		}
		
		return arcPoints;
	}
	
	/**
	 * Calculates the center coordinates from the given bounding coordinates
	 * @see <a href="https://math.stackexchange.com/questions/256694/how-to-calculate-center-point-in-geographic-coordinates#answer-260675">Center point in geographic coordinates</a>
	 * 
	 * @param latMin The lower latitude of the bound
	 * @param lonMin The lower longitude of the bound
	 * @param latMax The higher latitude of the bound
	 * @param lonMax The higher longitude of the bound
	 * @return The center coordinates from the bound
	 */
	public static Coordinate getCenterOfBoundingCoords(double latMin, double lonMin, double latMax, double lonMax) {
		final double radMinLat = toRadians(latMin); 
		final double radMinLon = toRadians(lonMin); 
		final double radMaxLat = toRadians(latMax); 
		final double radMaxLon = toRadians(lonMax); 
		final double x = ((cos(radMinLat) * cos(radMinLon)) + (cos(radMaxLat) * cos(radMaxLon))) / EARTH_RADIUS;
		final double y = ((cos(radMinLat) * sin(radMinLon)) + (cos(radMaxLat) * sin(radMaxLon)))/ EARTH_RADIUS;
		final double z = (sin(radMinLat) + sin(radMaxLat)) / EARTH_RADIUS;
		final double centerLat = atan2(z, sqrt((x*x+y*y)));
		final double centerLon = atan2(y,x);
		
		return new Coordinate(toDegrees(centerLat), toDegrees(centerLon));
	}
	
	/**
	 * Calculates the distance between two coordinates with the Haversine formula
	 * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a>
	 * 
	 * @param latMin The lower latitude of the bound
	 * @param lonMin The lower longitude of the bound
	 * @param latMax The higher latitude of the bound
	 * @param lonMax The higher longitude of the bound
	 * @return The distance between two coordinates in meters.
	 */
	public static double getDistanceBetweenTwoCoords(double latMin, double lonMin, double latMax, double lonMax) {
		final double radMinLat = toRadians(latMin); 
		final double radMinLon = toRadians(lonMin); 
		final double radMaxLat = toRadians(latMax); 
		final double radMaxLon = toRadians(lonMax); 
		final double phi = radMaxLat-radMinLat;
		final double lambda = radMaxLon-radMinLon;
		final double angle = hav(phi) + cos(radMinLat) * cos(radMaxLat) * hav(lambda);
		final double haversineResult = 2.D * EARTH_RADIUS * asin(sqrt(angle)); 
		
		return haversineResult;
	}
	
	private static double hav(double theta) {
		return (1-cos(theta))/2.D;
	}
	
}
