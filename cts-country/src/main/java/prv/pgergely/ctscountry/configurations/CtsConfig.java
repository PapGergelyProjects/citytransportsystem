package prv.pgergely.ctscountry.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import prv.pgergely.ctscountry.utils.Datasource;
import prv.pgergely.ctscountry.utils.ThreadParams;
import prv.pgergely.ctscountry.utils.docker.DockerCommands;

@Configuration
@ConfigurationProperties("config")
public class CtsConfig {
	// Deprecated
	private String transitFeedSource;
	private String transitFeedKey;
	
	private String mobilityApiUrl;
	private String mobilityApiTokenUrl;
	private String mobilityApiRefreshToken;
	private String tempDirectory;
	private ThreadParams threadParams;
	private Datasource datasource;
	private DockerCommands dockerCommands;
	
	//Test
	private String fixedToken;
	
	@Deprecated
	public String getTransitFeedSource() {
		return transitFeedSource;
	}
	public void setTransitFeedSource(String transitFeedSource) {
		this.transitFeedSource = transitFeedSource;
	}
	@Deprecated
	public String getTransitFeedKey() {
		return transitFeedKey;
	}
	public void setTransitFeedKey(String transitFeedKey) {
		this.transitFeedKey = transitFeedKey;
	}
	public String getMobilityApiUrl() {
		return mobilityApiUrl;
	}
	public void setMobilityApiUrl(String mobilityApiUrl) {
		this.mobilityApiUrl = mobilityApiUrl;
	}
	public String getMobilityApiTokenUrl() {
		return mobilityApiTokenUrl;
	}
	public void setMobilityApiTokenUrl(String mobilityApiTokenUrl) {
		this.mobilityApiTokenUrl = mobilityApiTokenUrl;
	}
	public String getMobilityApiRefreshToken() {
		return mobilityApiRefreshToken;
	}
	public void setMobilityApiRefreshToken(String mobilityApiRefreshToken) {
		this.mobilityApiRefreshToken = mobilityApiRefreshToken;
	}
	public String getTempDirectory() {
		return tempDirectory;
	}
	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}
	public ThreadParams getThreadParams() {
		return threadParams;
	}
	public void setThreadParams(ThreadParams threadParams) {
		this.threadParams = threadParams;
	}
	public Datasource getDatasource() {
		return datasource;
	}
	public void setDatasource(Datasource datasource) {
		this.datasource = datasource;
	}
	public DockerCommands getDockerCommands() {
		return dockerCommands;
	}
	public void setDockerCommands(DockerCommands dockerCommands) {
		this.dockerCommands = dockerCommands;
	}
	public String getFixedToken() {
		return fixedToken;
	}
	public void setFixedToken(String fixedToken) {
		this.fixedToken = fixedToken;
	}
}
