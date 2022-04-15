package prv.pgergely.ctsdata.config;

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
	
	
	private static String schema = "";
	private static Long feedId = 0L;
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
		String[] param = args[0].split("#");
		schema = param[0];
		feedId = Long.valueOf(param[1]);
		SpringApplication.run(ApplicationCtsDatasource.class, args);
	}
	
	@Bean
	public Schema getSchema() {
		Schema sc = new Schema();
		sc.setSchemaName(schema);
		sc.setFeedId(feedId);
		return sc;
	}
}
