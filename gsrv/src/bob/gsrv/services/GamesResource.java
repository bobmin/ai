package bob.gsrv.services;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bob.gsrv.dao.GameDao;
import bob.gsrv.model.Game;

@Path("/games")
public class GamesResource {

	@Context
	UriInfo uriInfo;

	final GameDao dao = new GameDao();

	public GamesResource() {
	}

	@GET
	@Produces("text/plain")
	public Response getGames() {
		return Response.ok().entity("Welcome here!").build();
	}

	/**
	 * Erstellt ein neues Spiel.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	@POST
	public Response createGame() {
		final Game x = dao.create();
		final int id = x.getId();
		final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
		final String txt = String.format("game %d created", id);
		return Response.created(uri).entity(txt).build();
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getGame(@PathParam("id") int id) {
		final Game x = dao.search(id);
		if (null == x) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.ok().entity(x.getTextBoard()).build();
	}

}
