package prv.pgergely.ctscountry.domain.mobility.gtfs;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import prv.pgergely.ctscountry.domain.mobility.feeds.MobilityFeeds;

public class MobilityGtfsFeeds extends MobilityFeeds implements Serializable {
	
	private List<Location> locations;
	
	@JsonProperty("latest_dataset")
	private LatestDataset latestData;

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public LatestDataset getLatestData() {
		return latestData;
	}

	public void setLatestData(LatestDataset latestData) {
		this.latestData = latestData;
	}

	@Override
	public String toString() {
		String superToString = super.toString().replace("MobilityFeeds", "").replace("{","").replace("}","");
		return "MobilityGtfsFeeds {"+superToString+", \nlocations:" + locations + ", \nlatestData:" + latestData + "\n}";
	}
	
}
