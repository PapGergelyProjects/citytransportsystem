package prv.pgergely.ctsdata.config;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.ctsdata.CtsDsComponents;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, CtsDsComponents.class})
public class ApplicationCtsDatasource extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(ApplicationCtsDatasource.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsDatasource.class, args);
	}
	
	@Bean
	public Queue<TransitFeedZipFile> getInternalStore(){
		return new LinkedBlockingQueue<TransitFeedZipFile>();
	}
}
