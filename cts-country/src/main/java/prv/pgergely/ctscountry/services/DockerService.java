package prv.pgergely.ctscountry.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.domain.docker.DockerContainer;
import prv.pgergely.ctscountry.model.DatasourceInfo;
import prv.pgergely.ctscountry.utils.docker.ContainerStatus;
import prv.pgergely.ctscountry.utils.docker.ProcessHandler;

@Service
public class DockerService {
	
	@Autowired
	private CtsConfig config;
	
	public Map<ContainerStatus, List<DockerContainer>> getContainers(ContainerStatus stat){
		Map<ContainerStatus, List<DockerContainer>> statusCont = new HashMap<>();
		List<DockerContainer> containers = new ArrayList<>();
		if(ContainerStatus.ALL.equals(stat)) {
			for(ContainerStatus status : ContainerStatus.getAllStatus()){
				containers = getContainersByStatus(status);
				statusCont.put(status, containers);
			}
		}else {
			containers = getContainersByStatus(stat);
			statusCont.put(stat, containers);
		}
        return statusCont;
	}
	
    private List<DockerContainer> getContainersByStatus(ContainerStatus status){
        List<DockerContainer> containers = new ArrayList<>();
        String command = config.getDockerCommands().getListContainer();
        Supplier<String> dockerCmd = ProcessHandler.execute.command(command.replace("<status>", status.getName())).getOutput();
        for(String line = dockerCmd.get(); line != null; line = dockerCmd.get()){
            String[] splitted = line.split(";");
            String dateTime = splitted[2].substring(0, 19);
            DockerContainer actualContainer = new DockerContainer(splitted[0], splitted[1], 
                    LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
                    splitted[3], splitted[5], splitted[6], splitted[7]);
            containers.add(actualContainer);
            
        }
        
        return containers;
    }
    
    public void createContainer(DatasourceInfo info) {
		String containerName = "GTFS-"+info.getFeedId();
		String schema = info.getSchema_name()+"#"+info.getFeedId();
		int accesPort = info.getPort();
		String image = config.getDockerCommands().getImageName();
		String dockerContainerRun = config.getDockerCommands().getCreateContainer();
		dockerContainerRun = dockerContainerRun.replace("<cont_name>", containerName).replace("<scheam_name>", schema).replace("<access_port>", String.valueOf(accesPort)).replace("<image_name>", image);
		
		ProcessHandler.execute.command(dockerContainerRun).getOutput();
    }
    
    public void startContainer(Long feedId) {
    	String containerName = "GTFS-"+feedId;
    	String startCommand = config.getDockerCommands().getStartContainer().replace("<cont_name>", containerName);
    	
    	ProcessHandler.execute.command(startCommand).getOutput();
    }
    
    public void stopContainer(Long feedId) {
    	String containerName = "GTFS-"+feedId;
    	String stopCommand = config.getDockerCommands().getStopContainer().replace("<cont_name>", containerName);
    	
    	ProcessHandler.execute.command(stopCommand).getOutput();
    }
}
