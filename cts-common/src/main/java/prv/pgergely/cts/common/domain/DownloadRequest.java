package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.net.URI;

/**
 * This class represents a download request for each ds service.
 * 
 * @author PapGergely
 *
 */
public class DownloadRequest implements Serializable {

	private final long feedId;
	private final String fileName;
	private final URI uriAddress;

	public DownloadRequest(long feedId, String fileName, URI uriAddress) {
		this.feedId = feedId;
		this.fileName = fileName;
		this.uriAddress = uriAddress;
	}
	
	public long getFeedId() {
		return feedId;
	}
	
	public String getFileName() {
		return fileName;
	}

	public URI getUriAddress() {
		return uriAddress;
	}

	@Override
	public String toString() {
		return "DownloadRequest {\n    feedId:" + feedId + ", \n    fileName:" + fileName + ", \n    uriAddress:" + uriAddress + "\n}";
	}
	
}
