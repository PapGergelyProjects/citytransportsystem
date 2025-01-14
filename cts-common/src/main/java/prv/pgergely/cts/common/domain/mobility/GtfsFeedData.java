package prv.pgergely.cts.common.domain.mobility;

import java.io.Serializable;

import prv.pgergely.cts.common.domain.CommonFeedData;

public class GtfsFeedData extends CommonFeedData implements Serializable {
	
	private BoundingCoordinates boundingCoord;
	private String countryCode;
	
	public BoundingCoordinates getBoundingCoord() {
		return boundingCoord;
	}

	public void setBoundingCoord(BoundingCoordinates boundingCoord) {
		this.boundingCoord = boundingCoord;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "GtfsFeedData {\n    boundingCoord:" + boundingCoord + ", \n    countryCode:" + countryCode + ", \n    " + super.toString() + "\n}";
	}
	
}
