package prv.pgergely.ctscountry.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import prv.pgergely.ctscountry.utils.Datasource;
import prv.pgergely.ctscountry.utils.ThreadParams;
import prv.pgergely.ctscountry.utils.docker.DockerCommands;

@Configuration
@ConfigurationProperties("config")
public class CtsConfig {
	
	private String transitFeedSource;
	private String transitFeedKey;
	private String tempDirectory;
	private ThreadParams threadParams;
	private Datasource datasource;
	private DockerCommands dockerCommands;
	
	public String getTransitFeedSource() {
		return transitFeedSource;
	}
	public void setTransitFeedSource(String transitFeedSource) {
		this.transitFeedSource = transitFeedSource;
	}
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
}
