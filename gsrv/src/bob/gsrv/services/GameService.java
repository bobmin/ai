package bob.gsrv.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/game")
public class GameService {

	@GET
	@Produces("text/plain")
	public String getGames() {
		return "games...";
	}

}
