package prv.pgergely.cts.common.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DefaultResponse implements Serializable {
	
	public String message;
	public String urlPart;
	public LocalDateTime timestamp = LocalDateTime.now();
	public int statusCode;
	
	@Override
	public String toString() {
		return "DefaultResponse {\n message: " + message + ",\n urlPart: " + urlPart + ",\n timestamp: " + timestamp
				+ ",\n statusCode: " + statusCode + "\n}";
	}
	
}
