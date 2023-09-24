package cts.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;

import cts.app.CtsAppComponent;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import prv.pgergely.cts.common.CommonComponents;

@Push
@Theme(value = "cts")
@EnableVaadin("cts.app.ui")
@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, CtsAppComponent.class})
public class ApplicationCts extends SpringBootServletInitializer implements AppShellConfigurator {//SpringBootServletInitializer
		
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

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}
	
}
