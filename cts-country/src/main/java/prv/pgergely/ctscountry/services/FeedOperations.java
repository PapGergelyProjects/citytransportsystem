package prv.pgergely.ctscountry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.model.FeedVersion;
import prv.pgergely.ctscountry.utils.docker.ProcessHandler;

@Service
public class FeedOperations {
	
	@Autowired
	private FeedVersionServiceImpl feedVersion;
	
	@Autowired
	private DatasourceService dsService;
	
	@Autowired
	private DockerService dockerSrvc;
	
	public void create(FeedVersion version) {//TODO: implement insertion, image creation handler.
		feedVersion.insert(version);
		DatasourceInfo info = dsService.insert(version);
		dockerSrvc.createContainer(info);
	}
	
	public void delete(FeedVersion version) {
		// TODO: docker container handling
		feedVersion.deleteFeedVersion(version);
		dsService.deleteDsService(version.getFeedId());
	}
}
