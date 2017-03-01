package bob.nn.activation;

/**
 * Eine Aktivierungsfunktion.
 * 
 * @author maik@btmx.net
 *
 */
public abstract class Activation {

	/** der Name */
	private final String name;

	public Activation(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	abstract public double computeFunction(final double value);

}
