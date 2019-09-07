package prv.pgergely.ctscountry.configurations;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.ctscountry.ApplicationCountryComponents;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationCountryComponents.class})
public class ApplicationCtsCountry extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(ApplicationCtsCountry.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsCountry.class, args);
	}
	
	@Bean(TemplateQualifier.DEFAULT_TEMPLATE)
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
}