package prv.pgergely.ctscountry.utils.docker;

import java.util.EnumSet;

public enum ContainerStatus {
	
    CREATED("created"),
    RESTARTING("restarting"),
    RUNNING("running"),
    REMOVING("removing"),
    PAUSED("paused"),
    EXITED("exited "),
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
}
