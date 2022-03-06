package prv.pgergely.cts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config")
public class CtsConfig {
	
	private String googleApiKey;
	private String googleMapLang;

	public String getGoogleApiKey() {
		return googleApiKey;
	}

	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	public String getGoogleMapLang() {
		return googleMapLang;
	}

	public void setGoogleMapLang(String googleMapLang) {
		this.googleMapLang = googleMapLang;
	}
	
}
