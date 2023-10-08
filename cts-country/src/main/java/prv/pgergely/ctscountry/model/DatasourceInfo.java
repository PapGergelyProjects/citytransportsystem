package prv.pgergely.ctscountry.model;

import java.io.Serializable;

import prv.pgergely.cts.common.domain.DataSourceState;

public class DatasourceInfo implements Serializable {
	
	private long id;
	private long feedId;
	private int port;
    private String sourceName;
    private String sourceUrl;
    private String schemaName;
    private DataSourceState state;
    private boolean active;
    
    protected DatasourceInfo() {}
    
    public DatasourceInfo(String sourceUrl, String schemaName, DataSourceState state, boolean active) {
    	this.sourceUrl = sourceUrl;
    	this.schemaName = schemaName;
    	this.state = state;
    	this.active = active;
    }
    
    public DatasourceInfo(long id, long feedId, String sourceName, String sourceUrl, String schemaName, DataSourceState state, boolean active) {
		this.id = id;
		this.feedId = feedId;
		this.sourceName = sourceName;
		this.sourceUrl = sourceUrl;
		this.schemaName = schemaName;
		this.state = state;
		this.active = active;
	}
    
	public DatasourceInfo(long feedId, int port, String sourceName, String sourceUrl, String schemaName, DataSourceState state, boolean active) {
		this.feedId = feedId;
		this.port = port;
		this.sourceName = sourceName;
		this.sourceUrl = sourceUrl;
		this.schemaName = schemaName;
		this.state = state;
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public DataSourceState getState() {
		return state;
	}

	public void setState(DataSourceState state) {
		this.state = state;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DatasourceInfo {\nid:" + id + ", \nfeedId:" + feedId + ", \nport:" + port + ", \nsourceName:"
				+ sourceName + ", \nsourceUrl:" + sourceUrl + ", \nschemaName:" + schemaName + ", \nstate:" + state
				+ ", \nactive:" + active + "\n}";
	}
	
}
