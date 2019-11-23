package prv.pgergely.ctscountry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.interfaces.DatasourceInfoDao;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class DatasourceService {
	
	@Autowired
	private DatasourceInfoDao repo;
	
	@Autowired
	private CtsConfig conf;
	
	public void insert(FeedVersion version) {
		long id = version.getFeedId();
		String title = version.getTitle();
		String techTitle = version.getTechnicalTitle();
		
		DatasourceInfo info = new DatasourceInfo(id, title, createEndpointFromData(id), createSchemaFromData(techTitle));
		repo.insert(info);
	}
	
	private String createEndpointFromData(final long feedId) {
		String url = conf.getDatasource().getUrl();
		String feedIdStr = String.valueOf(feedId);
		String portNumber=conf.getDatasource().getPort();
		for (int i = feedIdStr.length(); i < 3; i++) {
			portNumber+="0";
		}
		portNumber+=feedIdStr;
		return url+":"+portNumber;
	}
	
	private String createSchemaFromData(String title) {
		return title.replaceAll("[\\s,]", "_");
	}
	
	public void deleteDsService(long feedId) {
		repo.deleteDatasourceInfo(feedId);
	}
	
	public DatasourceInfo getByFeedId(long id) {
		return repo.getDatasourceInfoById(id);
	}
}
