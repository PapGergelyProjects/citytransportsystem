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
	
	public DatasourceInfo insert(FeedVersion version) {
		long id = version.getFeedId();
		String title = version.getTitle();
		String techTitle = version.getTechnicalTitle();
		String url = conf.getDatasource().getUrl();
		int port = createPortNumberFromFeedId(id);
		String endpoint = url+":"+port;
		
		DatasourceInfo info = new DatasourceInfo(id, port, title, endpoint, createSchemaFromData(techTitle));
		repo.insert(info);
		return info;
	}
	
	private int createPortNumberFromFeedId(final long feedId) {
		String portNumber=conf.getDatasource().getPort();
		String feedIdStr = String.valueOf(feedId);
		for (int i = feedIdStr.length(); i < 3; i++) {
			portNumber+="0";
		}
		portNumber+=feedIdStr;
		
		return Integer.valueOf(portNumber);
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
