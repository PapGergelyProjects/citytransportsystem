package prv.pgergely.ctscountry.domain.mobility.gtfs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
	
	@JsonProperty("country_code")
	private String countryCode;
	
	private String country;
	
	@JsonProperty("subdivision_name")
	private String subDivName;
	
	private String municipality;

	public String getCountryCode() {
		return countryCode;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getSubDivName() {
		return subDivName;
	}

	public void setSubDivName(String subDivName) {
		this.subDivName = subDivName;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	@Override
	public String toString() {
		return "Location {\ncountryCode:" + countryCode + ", \nsubDivName:" + subDivName + ", \nmunicipality:"
				+ municipality + "\n}";
	}
	
}
