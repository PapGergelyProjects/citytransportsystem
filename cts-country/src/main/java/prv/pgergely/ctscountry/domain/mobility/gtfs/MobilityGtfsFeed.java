package prv.pgergely.ctscountry.domain.mobility.gtfs;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import prv.pgergely.ctscountry.domain.mobility.feeds.MobilityFeed;

public class MobilityGtfsFeed extends MobilityFeed implements Serializable {
	
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
		String superToString = super.toString().replace("MobilityFeed", "").replace("{","").replace("}","");
		return "MobilityGtfsFeed {"+superToString+", \nlocations:" + locations + ", \nlatestData:" + latestData + "\n}";
	}
	
}
