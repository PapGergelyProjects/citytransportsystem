package prv.pgergely.ctscountry.model;

public class DatasourceInfo {
	
	private long id;
	private long feedId;
	private int port;
    private String source_name;
    private String source_url;
    private String schema_name;
    private boolean active;
    
    public DatasourceInfo(long id, long feedId, String source_name, String source_url, String schema_name, boolean active) {
		this.id = id;
		this.feedId = feedId;
		this.source_name = source_name;
		this.source_url = source_url;
		this.schema_name = schema_name;
		this.active = active;
	}
    
	public DatasourceInfo(long feedId, int port, String source_name, String source_url, String schema_name, boolean active) {
		super();
		this.feedId = feedId;
		this.port = port;
		this.source_name = source_name;
		this.source_url = source_url;
		this.schema_name = schema_name;
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
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getSchema_name() {
		return schema_name;
	}
	public void setSchema_name(String schema_name) {
		this.schema_name = schema_name;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "DatasourceInfo {\n id: " + id + ",\n feedId: " + feedId + ",\n port:" + port + ",\n source_name:" + source_name
				+ ",\n source_url: " + source_url + ",\n schema_name: " + schema_name + "\n, active: "+active+"}";
	}
	
}
