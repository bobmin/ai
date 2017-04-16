package bob.nn;

import java.util.Objects;

/**
 * Ein gewichtete Verbindung zu einem Neuron.
 * 
 * @author bobmin
 *
 */
public class Connection {

	/** das Quellneuron */
	private final Neuron source;

	/** die Gewichtung */
	private double weight = Math.random();

	/**
	 * Instanziiert das Objekt mit einem zufälligem Gewicht.
	 * 
	 * @param source
	 *            das Quellneuron
	 */
	public Connection(final Neuron source) {
		Objects.requireNonNull(source);
		this.source = source;
	}

	/**
	 * Liefert das Quellneuron.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	public Neuron getLeftNeuron() {
		return source;
	}

	/**
	 * Setz die Gewichtung.
	 * 
	 * @param value
	 *            die Gewichtung
	 */
	public void setWeight(final double value) {
		this.weight = value;
	}

	/**
	 * Liefert die aktuelle Gewichtung.
	 * 
	 * @return eine Zahl
	 */
	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return String.format("%s [neuron = %d, weight = %f]", this.getClass().getName(), source.getId(), weight);
	}

}
