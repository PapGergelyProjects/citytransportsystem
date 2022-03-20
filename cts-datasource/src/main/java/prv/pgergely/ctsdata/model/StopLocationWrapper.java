package prv.pgergely.ctsdata.model;

import java.util.LinkedList;
import java.util.List;

public class StopLocationWrapper {
	
	private List<StopLocation> stopList = new LinkedList<>();

	public StopLocationWrapper(List<StopLocation> stopList) {
		this.stopList.addAll(stopList);
	}

	public List<StopLocation> getStopList() {
		return stopList;
	}
}
