package bob.gsrv.services;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bob.gsrv.dao.PlayerDao;
import bob.gsrv.model.Player;

@Path("/player")
public class PlayerService {

	@Context
	UriInfo uriInfo;

	/** der Speicher für die Spieler */
	private final PlayerDao dao = new PlayerDao();

	public PlayerService() {
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPlayers(@QueryParam("n") String name) {
		if (null == name) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		final Player player = dao.search(name);
		if (null == player) {
			final String txt = String.format("name \"%s\" not found", name);
			return Response.noContent().entity(txt).build();
		} else {
			final int id = player.getId();
			final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
			final String txt = String.format("name \"%s\" found with id %d", name, id);
			return Response.ok().location(uri).entity(txt).build();
		}
	}

	/**
	 * Erstellt einen neuen Spieler mit Standardwerten.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	@PUT
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

	@POST
	@Path("/{id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response setPlayer(@PathParam("id") int id, Player p) {
		final Player x = dao.search(id);
		if (null == x) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		x.setName(p.getName());
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		return Response.status(Response.Status.ACCEPTED).contentLocation(uri).build();
	}

}
