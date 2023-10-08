package prv.pgergely.ctscountry.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.interfaces.DatasourceInfoRepo;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class DatasourceService {
	
	@Autowired
	private DatasourceInfoRepo repo;
	
	@Autowired
	private CtsConfig conf;
	
	public DatasourceInfo insert(FeedVersion version) {
		long id = version.getFeedId();
		String title = version.getTitle();
		String techTitle = version.getTechnicalTitle();
		String url = conf.getDatasource().getUrl();
		int port = createPortNumberFromFeedId(id);
		String endpoint = url+":"+port;
		
		DatasourceInfo info = new DatasourceInfo(id, port, title, endpoint, createSchemaFromData(techTitle), version.getState(), true);
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
	
	public void setDsActive(Long feedId) {
		repo.setActive(feedId);
	}
	
	public void deleteDsService(long feedId) {
		repo.deleteDatasourceInfo(feedId);
	}
	
	public DatasourceInfo getByFeedId(long id) {
		return repo.getDatasourceInfoById(id);
	}
	
	public List<DatasourceInfo> getAllInfo(){
		return repo.getAllDatasourceInfo();
	}
	
	public void updateSource(Long id, DataSourceState state) {
		repo.updateState(id, state);
	}
}
