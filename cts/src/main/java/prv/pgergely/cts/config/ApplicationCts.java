package prv.pgergely.cts.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.artur.helpers.LaunchUtil;

import com.vaadin.flow.component.dependency.NpmPackage;

import prv.pgergely.cts.ApplicationComponents;
import prv.pgergely.cts.common.CommonComponents;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationComponents.class})
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class ApplicationCts extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(ApplicationCts.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCts.class, args);
		//LaunchUtil.launchBrowserInDevelopmentMode();
	}
	
//	@Bean
//	public ServletRegistrationBean<SpringServlet> springServlet(ApplicationContext context) {
//	    return new ServletRegistrationBean<>(new SpringServlet(context, true), "/cts/*", "/frontend/*");
//	}
	
//    @Bean
//    public ServletRegistrationBean frontendServletBean() {
//        ServletRegistrationBean bean = new ServletRegistrationBean<>(new VaadinServlet() {
//            @Override
//            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                if (!serveStaticOrWebJarRequest(req, resp)) {
//                    resp.sendError(404);
//                }
//            }
//        }, "/frontend/*");
//        bean.setLoadOnStartup(1);
//        return bean;
//    }
}
