package prv.pgergely.ctscountry.services;

import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctscountry.interfaces.TemplateQualifier;

@Service
public class TransitFeedZipFileContent {
	
	@Autowired
	@Qualifier(TemplateQualifier.TRANSITFEED_ZIPFILE_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	@Qualifier(TemplateQualifier.DEFAULT_TEMPLATE)
	private RestTemplate defTemplate;
	
	@Value("${temp_directory}")
	private String tempFolder;
	
	public ResponseEntity<byte[]> getZipFile(String urlAddress){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Encoding", "UTF-8");
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
		
		return defTemplate.postForLocation(urlAddress, entity);
	}
}