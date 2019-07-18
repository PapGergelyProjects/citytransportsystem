package prv.pgergely.ctscountry.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("config")
public class CtsConfig {
	
	private String transitFeedKey;
	private String tempDirectory;
	
	public String getTransitFeedKey() {
		return transitFeedKey;
	}
	public void setTransitFeedKey(String transitFeedKey) {
		this.transitFeedKey = transitFeedKey;
	}
	public String getTempDirectory() {
		return tempDirectory;
	}
	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}
	
}
