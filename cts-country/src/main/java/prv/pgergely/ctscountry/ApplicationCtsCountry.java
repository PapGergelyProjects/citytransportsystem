package prv.pgergely.ctscountry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.interfaces.FixedThreadEngine;
import prv.pgergely.ctscountry.interfaces.TemplateQualifier;
import prv.pgergely.ctscountry.modules.FeedVersionHandler;
import prv.pgergely.ctscountry.services.DatabaseInit;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationCountryComponents.class})
public class ApplicationCtsCountry extends SpringBootServletInitializer {
	
	
	
	@Autowired
	private DatabaseInit initDb;
	
	@Autowired
	private FixedThreadEngine threadEng;
	
	@Autowired
	private FeedVersionHandler versionHandler;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(ApplicationCtsCountry.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsCountry.class, args);
	}
	
	@Bean
	@Qualifier(TemplateQualifier.DEFAULT_TEMPLATE)
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
	@PostConstruct
	public void initDatabase() {
		initDb.init();
		threadEng.process("VersionHandler", versionHandler);
	}
}