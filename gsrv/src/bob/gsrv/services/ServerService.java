package bob.gsrv.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/server")
public class ServerService {

	@GET
	@Produces("text/plain")
	public String getServerStatus() {
		return "server...";
	}

}
