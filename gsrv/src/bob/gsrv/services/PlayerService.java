package bob.gsrv.services;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import bob.gsrv.dao.PlayerDao;
import bob.gsrv.model.Player;

@Path("/player")
public class PlayerService {

	@Context
	UriInfo uriInfo;

	@GET
	@Produces("text/plain")
	public String getPlayers() {
		return "players...";
	}

	@POST
	public Response createPlayer() {
		final PlayerDao dao = new PlayerDao();
		final Player x = dao.create();
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(x.getId())).build();
		return Response.created(uri).build();
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_XML)
	public Player getPlayer(@PathParam("id") int id) {
		final PlayerDao dao = new PlayerDao();
		final Player x = dao.search(id);
		if (null == x) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return x;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response setPlayer(JAXBElement<Player> x) {
		final Player p = x.getValue();
		final PlayerDao dao = new PlayerDao();
		final Player u = dao.search(p.getId());
		if (null == u) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		u.setName(p.getName());
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(p.getId())).build();
		return Response.status(Response.Status.ACCEPTED).contentLocation(uri).build();
	}

}
