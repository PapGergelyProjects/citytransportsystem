package prv.pgergely.cts.common.domain.mobility.feeds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobilityFeed implements Serializable {
	
	private String id;
	
	@JsonProperty("data_type")
	private String dataType;
	
	private String features;
	
	private String status;
	
	@JsonProperty("created_at")
	private String createdAt;
	
	@JsonProperty("external_ids")
	private List<ExternalIds> externalId;
	
	private String provider;
	
	@JsonProperty("feed_name")
	private String feedName;
	
	private String note;
	
	@JsonProperty("feed_contact_email")
	private String contactMail;
	
	@JsonProperty("source_info")
	private SourceInfo sourceInfo;
	
	private List<String> redirects = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public List<ExternalIds> getExternalId() {
		return externalId;
	}

	public void setExternalId(List<ExternalIds> externalId) {
		this.externalId = externalId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public SourceInfo getSourceInfo() {
		return sourceInfo;
	}

	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	public List<String> getRedirects() {
		return redirects;
	}

	public void setRedirects(List<String> redirects) {
		this.redirects = redirects;
	}

	@Override
	public String toString() {
		return "MobilityFeed {\nid:" + id + ", \ndataType:" + dataType + ", \nstatus:" + status + ", \nexternalId:"
				+ externalId + ", \nprovider:" + provider + ", \nfeedName:" + feedName + ", \nnote:" + note
				+ ", \ncontactMail:" + contactMail + ", \nsourceInfo:" + sourceInfo + ", \nredirects:" + redirects
				+ "\n}";
	}
}
