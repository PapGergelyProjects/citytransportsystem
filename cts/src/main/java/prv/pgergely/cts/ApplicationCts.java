package prv.pgergely.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import prv.pgergely.cts.common.CommonComponents;

@SpringBootApplication
@ComponentScan(basePackageClasses=CommonComponents.class)
public class ApplicationCts extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(ApplicationCts.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCts.class, args);
	}
}
