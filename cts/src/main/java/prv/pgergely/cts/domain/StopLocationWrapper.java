package prv.pgergely.cts.domain;

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
