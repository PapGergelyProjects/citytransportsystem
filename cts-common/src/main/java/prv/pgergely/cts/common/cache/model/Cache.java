package prv.pgergely.cts.common.cache.model;

public interface Cache<T> {
	
	public T store();
	public long aliveTime();
	public long startTime();
	public default boolean isMarkedForDelete() {
		return false;
	}
	public default boolean isConstant() {
		return false;
	}
}
