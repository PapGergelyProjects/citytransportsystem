package prv.pgergely.cts.webservices;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/")
public class MainPage {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessage(@Context ServletContext ctx) {
		return "Hello "+ctx.getContextPath();
	}
	
	@GET
	@Path("/some")
	public String getSome() {
		return "Some more";
	}
}
