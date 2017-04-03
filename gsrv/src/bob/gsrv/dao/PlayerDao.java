package bob.gsrv.dao;

import java.util.HashMap;
import java.util.Map;

import bob.gsrv.model.Player;

public class PlayerDao {

	private static int counter = 0;

	private static final Map<Integer, Player> CACHE = new HashMap<>();

	public PlayerDao() {
	}

	public Player create() {
		final Player x = new Player();
		x.setId(counter);
		x.setName(String.format("Player %d", counter));
		CACHE.put(Integer.valueOf(counter), x);
		counter++;
		return x;
	}

	public Player search(int id) {
		return CACHE.get(Integer.valueOf(id));
	}

}
