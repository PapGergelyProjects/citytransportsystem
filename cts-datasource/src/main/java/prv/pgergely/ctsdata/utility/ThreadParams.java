package prv.pgergely.ctsdata.utility;

public class ThreadParams {
	
	private long initDelayed;
	private long delayBetween;
	
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

	@Override
	public String toString() {
		return "ThreadParams [initDelayed=" + initDelayed + ", delayBetween=" + delayBetween + "]";
	}
}
