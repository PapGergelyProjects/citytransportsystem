package prv.pgergely.cts.common.cache;

public interface Cache<T> {
	
	public T store();
	public default long getLifeTime() {
		return 5000L;
	}
	public long getStartTime();
}
