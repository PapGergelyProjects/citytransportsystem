package prv.pgergely.ctscountry.modules;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctscountry.configurations.TransitFeedsTemplate;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;

@Component
public class TransitFeedZipFileContent {
	
	@Autowired
	@Qualifier(TemplateQualifier.TRANSITFEED_ZIFILE_TEMPLATE)
	private RestTemplate template;
	
	@Value("${temp_directory}")
	private String tempFolder;
	
	public ResponseEntity<byte[]> getZipFile(String urlAddress){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		return template.exchange(urlAddress, HttpMethod.GET, entity, byte[].class);
	}
	
}
