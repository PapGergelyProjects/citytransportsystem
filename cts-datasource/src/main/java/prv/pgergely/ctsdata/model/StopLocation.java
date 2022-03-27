package prv.pgergely.ctsdata.model;

import java.time.LocalTime;
import java.util.List;

import prv.pgergely.cts.common.domain.Coordinate;


/**
 * This bean represents a stop location.
 * 
 * @author Pap Gergely
 *
 */
public class StopLocation{
	
	private long id;
	private String stopName;
	private String routeName;
	private double stopDistance;
	private String stopColor;
	private String stopTextColor;
	private List<LocalTime> departureTime;
	private Coordinate stopCoordinate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public double getStopDistance() {
		return stopDistance;
	}
	public void setStopDistance(double stopDistance) {
		this.stopDistance = stopDistance;
	}
	public String getStopColor() {
		return stopColor;
	}
	public void setStopColor(String stopColor) {
		this.stopColor = stopColor;
	}
	public String getStopTextColor() {
		return stopTextColor;
	}
	public void setStopTextColor(String stopTextColor) {
		this.stopTextColor = stopTextColor;
	}
	public Coordinate getStopCoordinate() {
		return stopCoordinate;
	}
	public void setStopCoordinate(Coordinate stopCoordinate) {
		this.stopCoordinate = stopCoordinate;
	}
	public List<LocalTime> getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(List<LocalTime> departureTime) {
		this.departureTime = departureTime;
	}
	
	@Override
	public String toString() {
		return "StopLocation [id=" + id + ", stopName=" + stopName + ", routeName=" + routeName + ", stopDistance="
				+ stopDistance + ", stopColor=" + stopColor + ", stopTextColor=" + stopTextColor + ", departureTime="
				+ departureTime + ", stopCoordinate=" + stopCoordinate + "]";
	}
	
}
