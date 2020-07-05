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
	private CtsConfig conf;
	
	public void create(FeedVersion version) {
		DatasourceInfo info = dsService.insert(version);
		String containerName = "GTFS-"+info.getFeedId();
		String schema = info.getSchema_name();
		int accesPort = info.getPort();
		String image = conf.getDockerCommands().getImageName();
		String dockerContainerRun = conf.getDockerCommands().getCreateContainer();
		dockerContainerRun = dockerContainerRun.replace("<cont_name>", containerName).replace("<scheam_name>", schema).replace("<access_port>", String.valueOf(accesPort)).replace("<image_name>", image);
		
		ProcessHandler.execute.command(dockerContainerRun).getOutput();
		
		feedVersion.insert(version);
	}
	
	public void delete(FeedVersion version) {
		// TODO: docker container handling
		feedVersion.deleteFeedVersion(version);
		dsService.deleteDsService(version.getFeedId());
	}
}
