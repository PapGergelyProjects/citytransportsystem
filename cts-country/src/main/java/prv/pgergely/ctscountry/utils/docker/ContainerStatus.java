package prv.pgergely.ctscountry.utils.docker;

import java.util.EnumSet;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public enum ContainerStatus {
	ALL("all"),
    CREATED("created"),
    RESTARTING("restarting"),
    RUNNING("running"),
    REMOVING("removing"),
    PAUSED("paused"),
    EXITED("exited"),
    DEAD("dead");
    
    private String status;
    private static EnumSet<ContainerStatus> allStatus = EnumSet.allOf(ContainerStatus.class);
    
    private ContainerStatus(String status){
        this.status = status;
    }
    
    public String getName(){
        return status;
    }
    
    public static EnumSet<ContainerStatus> getAllStatus(){
        return allStatus;
    }
    
    public static ContainerStatus getStatusByName(String name) throws HttpClientErrorException {
    	return allStatus.stream().filter(p -> p.getName().equals(name)).findFirst().orElseThrow(() ->new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
