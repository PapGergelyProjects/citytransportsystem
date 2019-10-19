package prv.pgergely.ctscountry.services;

import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.ctscountry.interfaces.ContentSenderThread;

@Service	
public class ZipContentSender implements ContentSenderThread {
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private Queue<TransitFeedZipFile> store;
	
	@Override
	public void run() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		while(store.size()>0) {
			TransitFeedZipFile zipFile = store.poll();
			HttpEntity<TransitFeedZipFile> entity = new HttpEntity<>(zipFile, headers);
			template.postForObject("<url from zip>", entity, String.class);
		}
	}

}
