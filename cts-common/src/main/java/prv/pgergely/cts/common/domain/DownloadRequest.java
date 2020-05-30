package prv.pgergely.cts.common.domain;

/**
 * This class represents a download request for the each ds service.
 * 
 * @author PapGergely
 *
 */
public class DownloadRequest {

	private long feedId;
	private String fileName;
	private String urlAddress;

	public DownloadRequest() {}
	
	public DownloadRequest(long feedId, String fileName, String urlAddress) {
		this.feedId = feedId;
		this.fileName = fileName;
		this.urlAddress = urlAddress;
	}
	
	public long getFeedId() {
		return feedId;
	}
	public String getFileName() {
		return fileName;
	}
	public String getUrlAddress() {
		return urlAddress;
	}

	@Override
	public String toString() {
		return "DownloadRequest {\n feedId: " + feedId + ",\n fileName: " + fileName + ",\n urlAddress: " + urlAddress + "\n}";
	}
	
}
