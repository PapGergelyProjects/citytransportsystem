package cts.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.LoadDependenciesOnStartup;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;

import cts.app.CtsAppComponent;
import cts.app.domain.ClickOnMapEvent;
import cts.app.ui.components.map.CtsGoogleMap;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.observable.ObservableObject;

@Push
@Theme(value = "cts")
@EnableVaadin("cts.app.ui")
@SpringBootApplication
@LoadDependenciesOnStartup
@ComponentScan(basePackageClasses= {CommonComponents.class, CtsAppComponent.class})
public class ApplicationCts extends SpringBootServletInitializer implements AppShellConfigurator {//SpringBootServletInitializer
	
	public static final String CHANGE_ON_UI_OBSERVABLE = "change-on-ui-event";
	public static final String CLICK_ON_MAP_OBSERVABLE = "click-on-map-event";
	
	@Autowired
	private CtsConfig config;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(ApplicationCts.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCts.class, args);
	}
	
	@Override
	public void configurePage(AppShellSettings settings) {
		settings.addFavIcon("icon", "favicon.png", "24x24");
		//settings.addLink("shortcut icon", "favicon.png");
	}

	@Bean
	@UIScope
	public CtsGoogleMap initMapService() {
		CtsGoogleMap map = new CtsGoogleMap(config, getEventObj());
		
		return map;
	}
	
	@Bean(CHANGE_ON_UI_OBSERVABLE)
	public ObservableObject<SourceState> getObsObj(){
		return new ObservableObject<>();
	}
	
	@Bean(CLICK_ON_MAP_OBSERVABLE)
	public ObservableObject<ClickOnMapEvent> getEventObj(){
		return new ObservableObject<>();
	}
	
	@Bean
	public Vertx createVertx() {
		VertxOptions opts = new VertxOptions().setWorkerPoolSize(10);
		return Vertx.vertx(opts);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}
	
}
