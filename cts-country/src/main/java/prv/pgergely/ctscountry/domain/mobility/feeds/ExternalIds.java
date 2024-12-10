package prv.pgergely.ctscountry.domain.mobility.feeds;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalIds implements Serializable{
	
	@JsonProperty("external_id")
	private String externalId;
	private String source;

	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "ExternalIds {\nexternalId:" + externalId + ", \nsource:" + source + "\n}";
	}
	
}
