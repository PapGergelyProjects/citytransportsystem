package prv.pgergely.ctscountry.utils.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessHandler {
	
    private Process process;
    private Logger logger = LogManager.getLogger(ProcessHandler.class);
    
    public static ProcessHandler execute = new ProcessHandler();
    
    private ProcessHandler(){}
    
    public ProcessHandler command(final String cmd){
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return this;
    }
    
    public Supplier<String> getOutput(){
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return () -> {
            try{
                return input.readLine(); 
            }catch(IOException e){
            	logger.error(e.getMessage(), e);
            }
            return null;
        };
    }
}
