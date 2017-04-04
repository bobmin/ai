package bob.gsrv.dao;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import bob.gsrv.model.Game;

public class GameDao {

	private static int counter = 0;

	private static final Map<Integer, Game> CACHE = new HashMap<>();

	public GameDao() {
	}

	public Game create() {
		final Game x = new Game();
		x.setId(counter);
		x.setName(String.format("Game %d", counter));
		CACHE.put(Integer.valueOf(counter), x);
		counter++;
		return x;
	}

	public Game search(int id) {
		return CACHE.get(Integer.valueOf(id));
	}

	public Set<Game> collect() {
		final Set<Game> x = new LinkedHashSet<>();
		x.addAll(CACHE.values());
		return x;
	}

}
