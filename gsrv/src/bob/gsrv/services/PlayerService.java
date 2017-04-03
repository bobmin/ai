package bob.gsrv.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/player")
public class PlayerService {

	@GET
	@Produces("text/plain")
	public String getPlayers() {
		return "players...";
	}

}
