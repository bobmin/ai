package bob.gsrv.dao;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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

	/**
	 * Sucht einen Spieler über dessen Namen.
	 * 
	 * @param name
	 *            der gesuchte Name
	 * @return ein Objekt oder <code>null</code>
	 */
	public Player search(String name) {
		for (Player x : CACHE.values()) {
			if (x.getName().equals(name)) {
				return x;
			}
		}
		return null;
	}

	public Set<Player> collect() {
		final Set<Player> x = new LinkedHashSet<>();
		x.addAll(CACHE.values());
		return x;
	}

}
