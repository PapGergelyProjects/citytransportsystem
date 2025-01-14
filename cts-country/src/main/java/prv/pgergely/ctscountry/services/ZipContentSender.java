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
import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.utils.TemplateQualifier;

@Service
public class ZipContentSender implements Runnable {
	
	@Autowired
	@Qualifier(TemplateQualifier.ZIPFILE_TEMPLATE)
	private RestTemplate template;
	
	@Autowired
	private Queue<DownloadRequest> store;
	
	@Autowired
	private DatasourceService dsService;
	
	private Logger logger = LogManager.getLogger(ZipContentSender.class);
	
	@Override
	public void run() {
		logger.info("Preparing to send download requests");
		while(store.size()>0) {
			DownloadRequest request = store.poll();
			DatasourceInfo info = dsService.getByFeedId(request.getFeedId());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Schema", info.getSchemaName());
			headers.set("X-Feed", info.getFeedId()+"");
			HttpEntity<DownloadRequest> entity = new HttpEntity<>(request, headers);
			try {
				DefaultResponse resp = template.postForObject(info.getSourceUrl()+"/api/update", entity, DefaultResponse.class);
				logger.info(request);
				logger.info(resp);
			} catch (Exception e) {
				logger.warn("Cannot send request to "+info.getSourceUrl()+" "+e);
			}
		}
	}

}
