package prv.pgergely.cts.common.domain;

import java.io.Serializable;

import elemental.json.Json;
import elemental.json.JsonObject;

public class SourceState implements Serializable {
	
	private Long feedId;
	private String from;
	private DataSourceState state;
	
	public SourceState() {}

	public SourceState(Long feedId, String from, DataSourceState state) {
		this.feedId = feedId;
		this.from = from;
		this.state = state;
	}

	public Long getFeedId() {
		return feedId;
	}
	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public DataSourceState getState() {
		return state;
	}
	public void setState(DataSourceState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "SourceState {\nfeedId:" + feedId + ", \nfrom:" + from + ", \nstate:" + state + "\n}";
	}
	
	public JsonObject toJsonObj() {
		JsonObject obj = Json.createObject();
		obj.put("feedId", feedId);
		obj.put("from", from);
		obj.put("state", state.name());
		
		return obj;
	}
	
}
