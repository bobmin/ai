package bob.gsrv.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/gsrv")
public class GsrvService {

	@GET
	@Produces("text/plain")
	public String getStatus() {
		return "gsrv...";
	}

}
