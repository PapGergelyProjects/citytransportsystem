package prv.pgergely.ctscountry.services;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.domain.DefaultResponse;
import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.ctscountry.interfaces.ContentSenderThread;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;
import prv.pgergely.ctscountry.model.DatasourceInfo;

@Service	
public class ZipContentSender implements ContentSenderThread {
	
	@Autowired
	@Qualifier(TemplateQualifier.ZIPFILE_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	private Queue<TransitFeedZipFile> store;
	
	@Autowired
	private DatasourceService dsService;
	
	private Logger logger = LogManager.getLogger(ZipContentSender.class);
	
	@Override
	public void run() {
		logger.info("Prepare to send zips");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		System.out.println(store);
		while(store.size()>0) {
			TransitFeedZipFile zipFile = store.poll();
			DatasourceInfo info = dsService.getByFeedId(zipFile.getFeedId());
			headers.set("X-Schema", info.getSchema_name());
			headers.set("X-Feed", info.getFeedId()+"");
			HttpEntity<byte[]> entity = new HttpEntity<>(zipFile.getZipStream(), headers);
			try {
				template.postForObject("http://"+info.getSource_url()+"/receive_zip", entity, byte[].class);
				logger.info(zipFile+" file sended.");
			} catch (Exception e) {
				logger.warn("Cannot send zip file to "+info.getSource_url()+" "+e);
			}
		}
	}

}
