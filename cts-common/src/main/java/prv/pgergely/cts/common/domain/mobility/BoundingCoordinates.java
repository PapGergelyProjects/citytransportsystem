package prv.pgergely.cts.common.domain.mobility;

import prv.pgergely.cts.common.domain.Coordinate;

public class BoundingCoordinates {
	
	private final Coordinate minBound;
	private final Coordinate maxBound;
	
	public BoundingCoordinates(double latMin, double lonMin, double latMax, double lonMax) {
		this.minBound = new Coordinate(latMin, lonMin);
		this.maxBound = new Coordinate(latMax, lonMax);
	}

	public Coordinate getMinBound() {
		return minBound;
	}

	public Coordinate getMaxBound() {
		return maxBound;
	}

	@Override
	public String toString() {
		return "BoundingCoordinates {\n    minBound:" + minBound + ", \n    maxBound:" + maxBound + "\n}";
	}
	
}
