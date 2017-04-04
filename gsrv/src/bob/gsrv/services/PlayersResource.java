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

@Path("/players")
public class PlayersResource {

	@Context
	UriInfo uriInfo;

	/** der Speicher für die Spieler */
	private final PlayerDao dao = new PlayerDao();

	public PlayersResource() {
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getPlayers(@QueryParam("n") String name) {
		if (null == name) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		final Player player = dao.search(name);
		if (null == player) {
			final String txt = String.format("name \"%s\" not found", name);
			return Response.noContent().entity(txt).build();
		} else {
			final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(player.getId())).build();
			return Response.ok().location(uri).entity(player).build();
		}
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPlayer(@PathParam("id") int id) {
		final PlayerDao dao = new PlayerDao();
		final Player x = dao.search(id);
		if (null == x) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.ok().entity(x).build();
	}

	/**
	 * Erstellt einen neuen Spieler mit Standardwerten.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	@POST
	public Response createPlayer() {
		final PlayerDao dao = new PlayerDao();
		final Player x = dao.create();
		final int id = x.getId();
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		final String txt = String.format("player %d created", id);
		return Response.created(uri).entity(txt).build();
	}

	/**
	 * Aktualisiert einen Spieler.
	 * 
	 * @param p
	 *            die Spielerdaten
	 * @return ein Objekt, niemals <code>null</code>
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updatePlayer(Player p) {
		if (null == p) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		final Player x = dao.search(p.getId());
		if (null == x) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		x.setName(p.getName());
		final int id = p.getId();
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		final String txt = String.format("player %d updated", id);
		return Response.status(Response.Status.ACCEPTED).contentLocation(uri).entity(txt).build();
	}

}
