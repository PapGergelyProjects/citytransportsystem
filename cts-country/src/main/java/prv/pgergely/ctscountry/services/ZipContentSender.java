package prv.pgergely.ctscountry.services;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.interfaces.ContentSenderThread;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;

@Service	
public class ZipContentSender implements ContentSenderThread {
	
	@Autowired
	@Qualifier(TemplateQualifier.DEFAULT_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	private Queue<TransitFeedZipFile> store;
	
	@Autowired
	private CtsConfig config;
	
	private Logger logger = LogManager.getLogger(ZipContentSender.class);
	
	@Override
	public void run() {
		logger.info("Prepare to send zips");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		System.out.println(store);
//		while(store.size()>0) {
//			TransitFeedZipFile zipFile = store.poll();
//			HttpEntity<TransitFeedZipFile> entity = new HttpEntity<>(zipFile, headers);
//			template.postForObject("<url from zip>", entity, String.class);
//			logger.info(zipFile+" file sended.");
//		}
	}

}
