package prv.pgergely.ctscountry.domain.mobility.feeds;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Redirects implements Serializable {

	@JsonProperty("target_id")
	private String targetId;
	private String comment;
	
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "{\n    targetId:" + targetId + ", \n    comment:" + comment + "\n}";
	}
	
}
