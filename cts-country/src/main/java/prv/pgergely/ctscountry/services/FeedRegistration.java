package prv.pgergely.ctscountry.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.model.FeedVersion;

//@Service
public class FeedRegistration {
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	@Autowired
	private DatasourceService dsService;
	
	@Autowired
	private CtsConfig conf;
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	public void apply(FeedVersion version) {
		feedVersion.insert(version);
		DatasourceInfo info = dsService.insert(version);
		String dockerContainerStart = "docker run --name %s -d -p %d:8080 %s --Dschema=\"%s\" ";
	}
}
