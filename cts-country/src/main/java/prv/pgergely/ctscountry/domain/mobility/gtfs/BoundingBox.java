package prv.pgergely.ctscountry.domain.mobility.gtfs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoundingBox implements Serializable {
	
	@JsonProperty("minimum_latitude")
	private Double minLat;
	
	@JsonProperty("maximum_latitude")
	private Double maxLat;
	
	@JsonProperty("minimum_longitude")
	private Double minLng;
	
	@JsonProperty("maximum_longitude")
	private Double maxLng;

	public Double getMinLat() {
		return minLat;
	}

	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}

	public Double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}

	public Double getMinLng() {
		return minLng;
	}

	public void setMinLng(Double minLng) {
		this.minLng = minLng;
	}

	public Double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(Double maxLng) {
		this.maxLng = maxLng;
	}

	@Override
	public String toString() {
		return "BoundingBox {\nminLat:" + minLat + ", \nmaxLat:" + maxLat + ", \nminLng:" + minLng + ", \nmaxLng:"
				+ maxLng + "\n}";
	}
}
