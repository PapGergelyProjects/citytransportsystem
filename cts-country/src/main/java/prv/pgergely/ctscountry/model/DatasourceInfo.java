package prv.pgergely.ctscountry.model;

public class DatasourceInfo {
	
	private long id;
	private long feedId;
    private String source_name;
    private String source_url;
    private String schema_name;
    
    public DatasourceInfo(long id, long feedId, String source_name, String source_url, String schema_name) {
		this.id = id;
		this.source_name = source_name;
		this.source_url = source_url;
		this.schema_name = schema_name;
	}
    
    public DatasourceInfo(long feedId, String source_name, String source_url, String schema_name) {
    	this.feedId = feedId; 
		this.source_name = source_name;
		this.source_url = source_url;
		this.schema_name = schema_name;
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

	@Override
	public String toString() {
		return "DatasourceInfo [id=" + id + ", source_name=" + source_name + ", source_url=" + source_url
				+ ", schema_name=" + schema_name + "]";
	}
}
