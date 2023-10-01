package prv.pgergely.ctscountry.model;

public enum DataSourceState {
	
	ONLINE("'ONLINE'::cts_transit_feed.state_enum"),
	OFFLINE("'OFFLINE'::cts_transit_feed.state_enum"),
	UPDATING("'UPDATING'::cts_transit_feed.state_enum"),
	UNREGISTERED("'UNREGISTERED'::cts_transit_feed.state_enum");
	
	private String pgSqlForm;
	
	private DataSourceState(String pgSqlForm) {
		this.pgSqlForm = pgSqlForm;
	}

	public String getPgSqlForm() {
		return pgSqlForm;
	}
	
}
