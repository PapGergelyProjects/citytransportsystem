package prv.pgergely.ctscountry.domain.mobility.token;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken implements Serializable {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expiration_datetime_utc")
	private String expirationDate;
	
	@JsonProperty("token_type")
	private String tokenType;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
}
