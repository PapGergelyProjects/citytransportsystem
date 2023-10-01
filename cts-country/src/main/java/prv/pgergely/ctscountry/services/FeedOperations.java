package prv.pgergely.ctscountry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.model.FeedVersion;

@Service
public class FeedOperations {
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	@Autowired
	private DatasourceService dsService;
	
	@Autowired
	private DockerService dockerSrvc;
	
	public void create(FeedVersion version) {
		feedVersion.insert(version);
		DatasourceInfo info = dsService.insert(version);
		//dockerSrvc.createContainer(info);
	}
	
	public void update(FeedVersion version) {
		feedVersion.update(version);
		dsService.setDsActive(version.getFeedId());
		//dockerSrvc.startContainer(version.getFeedId());
	}
	
	public void delete(FeedVersion version) {
		feedVersion.deleteFeedVersion(version);
		dsService.deleteDsService(version.getFeedId());
		dockerSrvc.stopContainer(version.getFeedId());
	}
}
