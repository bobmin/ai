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

	/**
	 * Berechnet die Funktion zum Wert.
	 * 
	 * @param value
	 *            der Wert
	 * @return eine Zahl
	 */
	abstract public double computeFunction(final double value);

	/**
	 * Berechnet die erste Ableitung zur Funktion.
	 * 
	 * @param value
	 *            der Wert
	 * @return eine Zahl
	 */
	abstract public double revertFunction(final double value);

}
