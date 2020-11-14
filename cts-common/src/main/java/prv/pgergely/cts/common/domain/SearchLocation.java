package prv.pgergely.cts.common.domain;

public class SearchLocation {
	
	private Coordinate coordinates;
	private double radius;
	
	public Coordinate getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "SearchLocation:{coordinates:" + coordinates + ", radius:" + radius + "}";
	}
	
}
