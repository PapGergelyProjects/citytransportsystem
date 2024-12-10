package prv.pgergely.ctscountry.domain.mobility.feeds;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceInfo implements Serializable {
	
	@JsonProperty("producer_url")
	private String producerUrl;
	
	@JsonProperty("authentication_type")
	private Integer authType;
	
	@JsonProperty("authentication_info_url")
	private String authInfoUrl;
	
	@JsonProperty("api_key_parameter_name")
	private String apiKeyParamName;
	
	@JsonProperty("license_url")
	private String licenseUrl;

	public String getProducerUrl() {
		return producerUrl;
	}

	public void setProducerUrl(String producerUrl) {
		this.producerUrl = producerUrl;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getAuthInfoUrl() {
		return authInfoUrl;
	}

	public void setAuthInfoUrl(String authInfoUrl) {
		this.authInfoUrl = authInfoUrl;
	}

	public String getApiKeyParamName() {
		return apiKeyParamName;
	}

	public void setApiKeyParamName(String apiKeyParamName) {
		this.apiKeyParamName = apiKeyParamName;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	@Override
	public String toString() {
		return "SourceInfo {\nproducerUrl:" + producerUrl + ", \nauthType:" + authType + ", \nauthInfoUrl:"
				+ authInfoUrl + ", \napiKeyParamName:" + apiKeyParamName + ", \nlicenseUrl:" + licenseUrl + "\n}";
	}
}
