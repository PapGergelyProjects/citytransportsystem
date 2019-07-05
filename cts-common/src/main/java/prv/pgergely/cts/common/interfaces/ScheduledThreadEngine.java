package prv.pgergely.cts.common.interfaces;

import java.util.concurrent.TimeUnit;

public interface ScheduledThreadEngine {
	
	public void process(long delayed, long timeQty, TimeUnit unit, Runnable... processes);
	public void process(long delayed, long timeQty, TimeUnit unit, String name, Runnable processes);
	public void shutdown();
	
}
