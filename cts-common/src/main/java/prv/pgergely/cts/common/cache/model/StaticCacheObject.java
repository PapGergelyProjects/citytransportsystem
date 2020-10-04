package prv.pgergely.cts.common.cache.model;

public class StaticCacheObject<T> implements Cache<T> {
	
	private T object;
	
	public StaticCacheObject(T object) {
		this.object = object;
	}
	
	@Override
	public T store() {
		return object;
	}

	@Override
	public long aliveTime() {
		return -1L;
	}

	@Override
	public long startTime() {
		return -1L;
	}

	@Override
	public boolean isConstant() {
		return true;
	}
	
	
}
