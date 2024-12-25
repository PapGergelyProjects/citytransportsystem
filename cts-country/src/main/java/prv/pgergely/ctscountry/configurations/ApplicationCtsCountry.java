package prv.pgergely.ctscountry.configurations;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctscountry.ApplicationCountryComponents;
import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;
import prv.pgergely.ctscountry.utils.TemplateQualifier;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationCountryComponents.class})
public class ApplicationCtsCountry extends SpringBootServletInitializer{
	
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
	
	@Bean
	public Queue<DownloadRequest> getInternalQueue() {
		return new LinkedBlockingQueue<DownloadRequest>();
	}
	
	@Bean
	public AtomicReference<AuthToken> getToken(){
		return new AtomicReference<>(new AuthToken());
	}
	
}