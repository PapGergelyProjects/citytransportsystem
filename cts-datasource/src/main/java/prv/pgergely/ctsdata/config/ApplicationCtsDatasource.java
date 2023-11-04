package prv.pgergely.ctsdata.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.ctsdata.CtsDsComponents;
import prv.pgergely.ctsdata.utility.Schema;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, CtsDsComponents.class})
public class ApplicationCtsDatasource {

	
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    	application.addCommandLineProperties(true);
//    	application.properties("spring.datasource-primary.schema="+getSchema().getSchemaName());
//
//    	return application.sources(ApplicationCtsDatasource.class);
//    }
    
//    private String getSchemaNameFromJNDI() {
//		try {
//			Context ctx = new InitialContext();
//			String schema = (String) ctx.lookup("java:/comp/env/global/schema");
//			return schema;
//		} catch (NamingException e) {
//			return "test_gtfs";
//		}
//    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsDatasource.class, args);
	}
	
	@Bean
	public Schema getSchema(ApplicationArguments arguments) {
		String[] param = arguments.getSourceArgs()[0].split("#");
		String schema = param[0];
		Long feedId = Long.valueOf(param[1]);
		Schema actSchema = new Schema();
		actSchema.setSchemaName(schema);
		actSchema.setFeedId(feedId);
		
		return actSchema;
	}

}
