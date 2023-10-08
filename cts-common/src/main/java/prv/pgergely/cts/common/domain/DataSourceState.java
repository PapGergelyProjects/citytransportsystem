package prv.pgergely.cts.common.domain;

public enum DataSourceState {
	
	ONLINE(),
	OFFLINE(),
	UPDATING(),
	UNREGISTERED(),
	TECHNICAL();
	
	
	private DataSourceState() {}
	
}
