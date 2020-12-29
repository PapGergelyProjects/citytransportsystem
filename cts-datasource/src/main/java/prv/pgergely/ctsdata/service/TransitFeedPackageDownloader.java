package prv.pgergely.ctsdata.service;

import java.net.URI;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctsdata.config.TemplateConfig;

@Service
public class TransitFeedPackageDownloader {
	
	@Autowired
	@Qualifier(TemplateConfig.TRANSITFEED_ZIPFILE_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	@Qualifier(TemplateConfig.DEFAULT_TEMPLATE)
	private RestTemplate defTemplate;
	
	private Logger logger = LogManager.getLogger(TransitFeedPackageDownloader.class);
	
	public ResponseEntity<byte[]> getZipFile(String urlAddress){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Encoding", "gzip");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		return template.exchange(urlAddress, HttpMethod.GET, entity, byte[].class);
	}
	
	public URI getLinkFromLocation(String urlAddress){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.set("Content-Encoding", "UTF-8");
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
			return defTemplate.postForLocation(urlAddress, entity);
		}catch(HttpClientErrorException e) {
			logger.warn(e.getStatusCode()+" - "+e.getStatusText());
		}
		
		return null;
	}
}
