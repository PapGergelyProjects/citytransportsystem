package prv.pgergely.cts.common.domain;

import java.io.Serializable;

public class TransitFeedZipFile implements Serializable {
	
	private static final long serialVersionUID = 4514814349821146885L;
	
	private String fileName;
	private byte[] zipStream;
	
	public TransitFeedZipFile(String fileName, byte[] zipStream) {
		this.fileName = fileName;
		this.zipStream = zipStream;
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
}