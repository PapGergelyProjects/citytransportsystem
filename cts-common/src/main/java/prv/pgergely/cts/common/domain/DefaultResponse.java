package prv.pgergely.cts.common.domain;

import java.time.LocalDateTime;

public class DefaultResponse {
	
	public String message;
	public String urlPart;
	public LocalDateTime timestamp = LocalDateTime.now();
	public int statusCode;
}
