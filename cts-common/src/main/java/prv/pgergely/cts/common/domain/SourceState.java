package prv.pgergely.cts.common.domain;

public class SourceState {
	
	private Long feedId;
	private String from;
	private String state;
	
	public SourceState() {}

	public SourceState(Long feedId, String from, String state) {
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "SourceState {\nfeedId:" + feedId + ", \nfrom:" + from + ", \nstate:" + state + "\n}";
	}
	
}
