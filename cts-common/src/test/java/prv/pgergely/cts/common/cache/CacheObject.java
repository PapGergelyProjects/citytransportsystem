package prv.pgergely.cts.common.cache;

public class CacheObject implements Cache<String>{
	
	private String data; 
	private long startTime;
	
	public CacheObject(String data) {
		this.data = data;
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public String store() {
		return data;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 7;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + (int) (startTime ^ (startTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		CacheObject other = (CacheObject) obj;
		if(this.hashCode() == other.hashCode()) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
