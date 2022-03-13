package prv.pgergely.ctscountry.utils;

public class ThreadParams {
	
	private long initDelayed;
	private long delayBetween;
	private long offset;
	
	public long getInitDelayed() {
		return initDelayed;
	}
	public void setInitDelayed(long initDelayed) {
		this.initDelayed = initDelayed;
	}
	public long getDelayBetween() {
		return delayBetween;
	}
	public void setDelayBetween(long delayBetween) {
		this.delayBetween = delayBetween;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
}
