package prv.pgergely.cts.common.cache.model;

public class ShortLiveCacheObject<T> implements Cache<T> {
	
	private T object;
	private long start = System.currentTimeMillis();
	private boolean deleteMarker;
	
	public ShortLiveCacheObject(T object) {
		this.object = object;
	}
	
	@Override
	public T store() {
		return object;
	}

	@Override
	public long aliveTime() {
		return 5000L;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 7;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		
		return result;
	}

	@Override
	public long startTime() {
		return start;
	}

	@Override
	public boolean isMarkedForDelete() {
		return deleteMarker;
	}
	
}
