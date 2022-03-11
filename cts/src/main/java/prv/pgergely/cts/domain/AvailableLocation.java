package prv.pgergely.cts.domain;

import com.helger.commons.name.IHasDisplayName;

public class AvailableLocation implements IHasDisplayName {
	
	private Long id;
	private String locationName;
	private Double lat;
	private Double lon;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	@Override
	public String getDisplayName() {
		return locationName;
	}
	
}
