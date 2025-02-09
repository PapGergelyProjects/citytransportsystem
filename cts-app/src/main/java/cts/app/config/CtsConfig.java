package cts.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config")
public class CtsConfig {
	
	private String googleApiKey;
	private String googleMapLang;
	private String googleMapId;
	private String serviceUrl;
	private String defaultCountry;
	
	public String getGoogleApiKey() {
		return googleApiKey;
	}

	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	public String getGoogleMapLang() {
		return googleMapLang;
	}
	
	public String getGoogleMapId() {
		return googleMapId;
	}

	public void setGoogleMapId(String googleMapId) {
		this.googleMapId = googleMapId;
	}

	public void setGoogleMapLang(String googleMapLang) {
		this.googleMapLang = googleMapLang;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getDefaultCountry() {
		return defaultCountry;
	}

	public void setDefaultCountry(String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}
	
}
