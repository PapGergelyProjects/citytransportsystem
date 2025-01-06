package prv.pgergely.cts.common.domain.mobility;

import java.io.Serializable;

import prv.pgergely.cts.common.domain.CommonFeedData;

public class GtfsFeedData extends CommonFeedData implements Serializable {
	
	private BoundingCoordinates boundingCoord;
	
	public BoundingCoordinates getBoundingCoord() {
		return boundingCoord;
	}

	public void setBoundingCoord(BoundingCoordinates boundingCoord) {
		this.boundingCoord = boundingCoord;
	}

	@Override
	public String toString() {
		return "GtfsFeedData {\n    boundingCoord:" + boundingCoord + ", \n    toString():" + super.toString() + "\n}";
	}
	
}
