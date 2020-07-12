package prv.pgergely.ctscountry.webservices;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.domain.docker.DockerContainer;
import prv.pgergely.ctscountry.services.DockerService;
import prv.pgergely.ctscountry.utils.docker.ContainerStatus;

@RestController
@RequestMapping(path="/docker-mgmt")
public class DockerManagementService {
	
	@Autowired
	private DockerService dockerSrvc;
	
	@RequestMapping(path="/container", method = RequestMethod.HEAD)
	public ResponseEntity<Void> getContainerData(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Docker","");
		return new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(path="/container/list/{status}")
	public ResponseEntity<Map<ContainerStatus, List<DockerContainer>>> listContainers(@PathVariable String status){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		headers.set("X-Docker-Containers", "True");
		headers.set("X-Container-Status", status.toUpperCase());
		try {
			ContainerStatus pathStatus = ContainerStatus.getStatusByName(status);
			return new ResponseEntity<Map<ContainerStatus, List<DockerContainer>>>(dockerSrvc.getContainers(pathStatus), headers, HttpStatus.ACCEPTED);
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<Map<ContainerStatus, List<DockerContainer>>>(e.getStatusCode());
		}
	}
	
	@PutMapping(path="/container/create")
	public ResponseEntity<Void> createContainer(@RequestBody String createData){
		
		
		return ResponseEntity.created(null).body(null);
	}
}
