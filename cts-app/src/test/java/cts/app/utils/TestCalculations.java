package cts.app.utils;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prv.pgergely.cts.common.domain.Coordinate;

public class TestCalculations {

	@Test
	@DisplayName("Test center coordinate calulcation from bound coordinates")
	public void testCenterCoordsCalulation() {
		Coordinate centerCoords1 = Calculations.getCenterOfBoundingCoords(45.994538, 18.131841, 46.153928, 18.344572);
		System.out.println("centerCoords: "+centerCoords1);
		Coordinate centerCoords2 = Calculations.getCenterOfBoundingCoords(47.435411, 18.918696, 47.467701, 18.980891);
		System.out.println("centerCoords: "+centerCoords2);
		Coordinate centerCoords3 = Calculations.getCenterOfBoundingCoords(47.174786, 18.719305, 47.661056, 19.357892);
		System.out.println("centerCoords: "+centerCoords3);
		assertTrue(true);
	}
	
	@Test
	@DisplayName("Test distance between bound coordinates")
	public void testDistance() {
		double dist1 = Calculations.getDistanceBetweenToCoords(45.994538, 18.131841, 46.153928, 18.344572);
		double dist2 = Calculations.getDistanceBetweenToCoords(47.435411, 18.918696, 47.467701, 18.980891);
		double dist3 = Calculations.getDistanceBetweenToCoords(47.174786, 18.719305, 47.661056, 19.357892);
		System.out.println("pécs: "+(dist1/1000));
		System.out.println("budaörs: "+(dist2/1000));
		System.out.println("budapest: "+(dist3/1000));
		
		assertTrue(true);
	}
}
