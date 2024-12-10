package prv.pgergely.ctscountry.domain.mobility.gtfs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatestDataset implements Serializable {
	
	private String id;
	
	@JsonProperty("hosted_url")
	private String hostedUrl;
	
	@JsonProperty("bounding_box")
	private BoundingBox boundinBox;
	
	@JsonProperty("downloaded_at")
	private String downloadAt;
	
	private String hash;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostedUrl() {
		return hostedUrl;
	}

	public void setHostedUrl(String hostedUrl) {
		this.hostedUrl = hostedUrl;
	}

	public BoundingBox getBoundinBox() {
		return boundinBox;
	}

	public void setBoundinBox(BoundingBox boundinBox) {
		this.boundinBox = boundinBox;
	}

	public String getDownloadAt() {
		return downloadAt;
	}

	public void setDownloadAt(String downloadAt) {
		this.downloadAt = downloadAt;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "LatestDataset {\nid:" + id + ", \nhostedUrl:" + hostedUrl + ", \nboundinBox:" + boundinBox
				+ ", \ndownloadAt:" + downloadAt + ", \nhash:" + hash + "\n}";
	}
}
