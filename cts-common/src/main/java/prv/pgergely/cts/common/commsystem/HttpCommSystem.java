package prv.pgergely.cts.common.commsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HttpCommSystem {
	
	private final Logger LOGGER = LogManager.getLogger(HttpCommSystem.class);
	
	public HttpCommSystem() {}
	
	public String getRequest(String urlAddress) throws IOException{
		StringBuilder sb = new StringBuilder();
		HttpURLConnection connSec = establishConnection(urlAddress);
		try(BufferedReader bfr = new BufferedReader(new InputStreamReader(connSec.getInputStream()))){
			while(bfr.ready()){
				sb.append(bfr.readLine());
			}
			return sb.toString();
		}catch(IOException e){
			LOGGER.error(e);
			return "";
		}
	}
	
    public HttpURLConnection establishConnection(String urlAddress) throws IOException{
    	URL packUrl = new URL(urlAddress);
    	try{
    		HttpsURLConnection conn = (HttpsURLConnection)packUrl.openConnection();
    		conn.getResponseCode();
    		return conn;
    	}catch(ConnectException | MalformedURLException | ClassCastException e){
    		try{
    			LOGGER.warn("HTTPS connection cannot be established!");
    			LOGGER.warn("Try to establish HTTP connection...");
    			HttpURLConnection conn = (HttpURLConnection)packUrl.openConnection();
    			conn.getResponseCode();
    			LOGGER.warn("HTTP connection established.");
    			return conn;
    		}catch(ConnectException | MalformedURLException ex){
    			LOGGER.error("Cannot establish any kind of connection: "+ex);
    			throw new ConnectException(ex.getMessage());
    		}
    	}
    }
}
