package bob.nn;

/**
 * Beschreibt den zentralen Kern von jedem Neuron.
 * 
 * @author bobmin
 *
 */
abstract public class Neuron {

	/** der Zähler über alle Neuronen */
	private static int counter = 0;

	/** die eindeutige Nummer vom Neuron */
	private final int id;

	/** der Name vom Neuron */
	private String name = "NO NAME";

	/** der Wert vor Aktivierung */
	protected double value = Double.NaN;

	/**
	 * Instanziiert das Neuron und setzt dessen eindeutige Nummer.
	 */
	public Neuron() {
		id = counter;
		counter++;
	}

	/**
	 * Liefert die eindeutige Nummer vom Neuron.
	 * 
	 * @return eine Zahl
	 */
	public int getId() {
		return id;
	}

	/**
	 * Liefert den Ausgabewert vom Neuron.
	 * 
	 * @return eine Zahl
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		final String valueDisplay = Double.toString(value);
		return String.format("%s [id = %d, value = %s]", this.getClass().getName(), id, valueDisplay);
	}

}
