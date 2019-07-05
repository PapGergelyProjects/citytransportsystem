package prv.pgergely.cts.common.interfaces;

/**
 * This engine responsible for the implementation of fixed thread pool, 
 * which can starts and run process on a fixed scale.
 * Useful when we want to run some task async.
 * @author Pap Gergely
 *
 */
public interface FixedThreadEngine {
	
	public void process(String name, Runnable processes);
	public void shutdown();
	
}
