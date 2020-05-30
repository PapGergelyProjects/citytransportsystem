package prv.pgergely.ctscountry.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import prv.pgergely.ctscountry.domain.docker.DockerContainer;
import prv.pgergely.ctscountry.utils.docker.ContainerStatus;
import prv.pgergely.ctscountry.utils.docker.ProcessHandler;

@Service
public class DockerService {

	public Map<ContainerStatus, List<DockerContainer>> getContainers(){
		Map<ContainerStatus, List<DockerContainer>> statusCont = new HashMap<>();
		List<DockerContainer> containers = new ArrayList<>();
        for(ContainerStatus status : ContainerStatus.getAllStatus()){
            containers = launchCommand("docker container ps -a --format \"{{.ID}};{{.Image}};{{.CreatedAt}};{{.Ports}};{{.Status}};{{.Size}};{{.Names}};{{.Networks}}\" --filter status="+status.getName(), status);
            statusCont.put(status, containers);
        }
        return statusCont;
	}
	
    private List<DockerContainer> launchCommand(String command, ContainerStatus status){
        List<DockerContainer> containers = new ArrayList<>();
        Supplier<String> dockerCmd = ProcessHandler.execute.command(command).getOutput();
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
}