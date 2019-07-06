package prv.pgergely.cts.domain;

import prv.pgergely.cts.common.domain.Coordinate;

public class SearchValues {
	
	private Coordinate searchCoordinate;
	private double radius;
	
	public Coordinate getSearchCoordinate() {
		return searchCoordinate;
	}
	public void setSearchCoordinate(Coordinate searchCoordinate) {
		this.searchCoordinate = searchCoordinate;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "SearchValues [searchCoordinate=" + searchCoordinate + ", radius=" + radius + "]";
	}
}
