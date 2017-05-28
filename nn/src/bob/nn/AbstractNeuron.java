package bob.nn;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Beschreibt den zentralen Kern von jedem Neuron.
 * 
 * @author bobmin
 *
 */
abstract public class AbstractNeuron {

	/** der Zähler über alle Neuronen */
	private static int counter = 0;

	/** die eindeutige Nummer vom Neuron */
	private final int id;

	/** der Wert vor Aktivierung */
	protected double value = Double.NaN;

	/** die gewichteten Eingänge */
	protected final Set<Connection> incoming = new LinkedHashSet<>();

	/** die gewichteten Ausgänge */
	protected final Set<Connection> outgoing = new LinkedHashSet<>();

	/**
	 * Instanziiert das Neuron und setzt dessen eindeutige Nummer.
	 */
	public AbstractNeuron() {
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
	 * Fügt einen Eingang mit zufälligem Gewicht hinzu.
	 * 
	 * @param source
	 *            das Quellneuron
	 */
	public void registerIncoming(final Connection connection) {
		incoming.add(connection);
	}

	/**
	 * Liefert die gewichteten Eingänge.
	 * 
	 * @return ein Object, niemals <code>null</code>
	 */
	public Set<Connection> getIncoming() {
		return incoming;
	}

	public void registerOutgoing(final Connection connection) {
		outgoing.add(connection);
	}

	/**
	 * Liefert die Ausgänge vom Neuron.
	 * 
	 * @return ein Object, niemals <code>null</code>
	 */
	public Set<Connection> getOutgoing() {
		return outgoing;
	}

	/**
	 * Liefert den Wert vor Aktivierung.
	 * 
	 * @return eine Zahl oder {@link Double#NaN}
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Liefert den Ausgabewert vom Neuron.
	 * 
	 * In der Eingangsschicht liefern die Neuronen ihren Wert.
	 * 
	 * Bei versteckten Schichten und in der Ausgabeschicht wird der Wert über
	 * einen Aktivierungsfunktion geliefert.
	 * 
	 * @return eine Zahl oder {@link Double#NaN}
	 */
	abstract public double getOutput();

}
