package prv.pgergely.cts.common.domain;

public class SourceState {
	
	private String from;
	private String state;
	
	public SourceState() {}

	public SourceState(String from, String state) {
		this.from = from;
		this.state = state;
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
		return "SourceState{from:" + from + ",\n state:" + state + "}";
	}
	
}
