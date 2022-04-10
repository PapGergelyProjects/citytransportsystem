package prv.pgergely.ctsdata.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import prv.pgergely.ctsdata.utility.ThreadParams;

@Configuration
@ConfigurationProperties("configs")
public class CtsDataConfig {
	
	private String websocketServer;
	private ThreadParams threadParams;
	private String tempDirectory;
	private List<String> tables;
	
	public String getWebsocketServer() {
		return websocketServer;
	}

	public void setWebsocketServer(String websocketServer) {
		this.websocketServer = websocketServer;
	}

	public String getTempDirectory() {
		return tempDirectory;
	}

	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public ThreadParams getThreadParams() {
		return threadParams;
	}

	public void setThreadParams(ThreadParams threadParams) {
		this.threadParams = threadParams;
	}

	@Override
	public String toString() {
		return "CtsDataConfig {\nwebsocketServer:" + websocketServer + ", \nthreadParams:" + threadParams
				+ ", \ntempDirectory:" + tempDirectory + ", \ntables:" + tables + "\n}";
	}
}
