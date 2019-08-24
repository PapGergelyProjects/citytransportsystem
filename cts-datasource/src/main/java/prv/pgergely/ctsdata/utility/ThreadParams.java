package prv.pgergely.ctsdata.utility;

public class ThreadParams {
	
	private long delay;
	private long timeQty;
	
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public long getTimeQty() {
		return timeQty;
	}
	public void setTimeQty(long timeQty) {
		this.timeQty = timeQty;
	}
	
	@Override
	public String toString() {
		return "ThreadParams [delay=" + delay + ", timeQty=" + timeQty + "]";
	}
}
