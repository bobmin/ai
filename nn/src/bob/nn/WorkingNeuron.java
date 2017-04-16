package bob.nn;

import java.util.LinkedHashSet;
import java.util.Set;

import bob.nn.activation.Activation;
import bob.nn.activation.LinearActivation;

/**
 * Beschreibt ein Neuron für die Ausgabeschicht oder für eine versteckte
 * Schicht. Ein solches Neuron bezieht seinen Wert (vor Aktivierung) aus
 * gewichteten Verbindungen zur vorher gelagerten Schicht.
 * 
 * @author bobmin
 *
 */
public class WorkingNeuron extends Neuron {

	/** die gewichteten Eingänge */
	private final Set<Connection> connections = new LinkedHashSet<>();

	/** die Aktivierungsfunktion */
	private Activation activation = new LinearActivation();

	private double output = Double.NaN;

	public WorkingNeuron() {
	}

	/**
	 * Setzt die Aktivierungsfunktion.
	 * 
	 * @param fkt
	 *            die Funktion
	 */
	public void setActivation(final Activation fkt) {
		this.activation = fkt;
	}

	/**
	 * Fügt einen Eingang mit zufälligem Gewicht hinzu.
	 * 
	 * @param source
	 *            das Quellneuron
	 */
	public void putInput(final Neuron source) {
		connections.add(new Connection(source));
	}

	public double getOutput() {
		return output;
	}

	/**
	 * Liefert die gewichteten Eingänge.
	 * 
	 * @return ein Object, niemals <code>null</code>
	 */
	public Set<Connection> getInputs() {
		return connections;
	}

	/**
	 * Berechnet den Wert und dessen Aktivierung. Das Ergebnis der Aktivierung
	 * entspricht der Ausgabe vom Neuron.
	 */
	public void activate() {
		this.value = 0;
		for (Connection c : connections) {
			this.value += (c.getWeight() * c.getLeftNeuron().getValue());
		}
		output = activation.computeFunction(this.value);
	}

	@Override
	public String toString() {
		return String.format("%s [id = %d, value = %s, activation = %s, output = %s]", this.getClass().getName(),
				getId(), Double.toString(getValue()), activation.getName(), Double.toString(output));
	}

}
