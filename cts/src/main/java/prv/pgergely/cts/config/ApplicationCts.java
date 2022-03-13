package prv.pgergely.cts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.ApplicationComponents;
import prv.pgergely.cts.common.CommonComponents;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationComponents.class})
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class ApplicationCts extends SpringBootServletInitializer {
		
	@Autowired
	private CtsConfig config;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(ApplicationCts.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCts.class, args);
	}
	
	@Bean
	@UIScope
	public GoogleMap mapService() {
		return new GoogleMap(config.getGoogleApiKey(), "", config.getGoogleMapLang());
	}
}
