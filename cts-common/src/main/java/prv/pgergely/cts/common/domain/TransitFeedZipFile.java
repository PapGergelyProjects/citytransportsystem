package prv.pgergely.cts.common.domain;

import java.io.Serializable;

public class TransitFeedZipFile implements Serializable {
	
	private static final long serialVersionUID = 4514814349821146885L;
	
	private long feedId;
	private String fileName;
	private byte[] zipStream;
	
	public TransitFeedZipFile(long feedId, String fileName, byte[] zipStream) {
		this.feedId = feedId;
		this.fileName = fileName;
		this.zipStream = zipStream;
	}
	
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getZipStream() {
		return zipStream;
	}
	public void setZipStream(byte[] zipStream) {
		this.zipStream = zipStream;
	}

	@Override
	public String toString() {
		return "TransitFeedZipFile: "+feedId;
	}
	
}
