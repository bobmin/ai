package bob.nn;

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
public class WorkingNeuron extends AbstractNeuron {

	/** die Aktivierungsfunktion */
	private Activation activation = new LinearActivation();

	/** der Wert nach Aktivierung */
	private double output = Double.NaN;

	/** die Fehlerkorrektur */
	private double delta = Double.NaN;

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

	public Activation getActivation() {
		return activation;
	}

	public void setIncomingWeights(double... values) {
		int idx = 0;
		for (Connection c : incoming) {
			c.setWeight(values[idx]);
			idx++;
		}
	}

	public void adjustIncomingWeights(double d) {
		this.delta = d;
		for (Connection c : incoming) {
			c.adjustWeight(d);
		}
	}

	public double getDelta() {
		return delta;
	}

	/**
	 * Berechnet den Wert und dessen Aktivierung. Das Ergebnis der Aktivierung
	 * entspricht der Ausgabe vom Neuron.
	 */
	public void activate() {
		this.value = 0;
		for (Connection c : incoming) {
			this.value += c.computeOutput();
		}
		output = activation.computeFunction(this.value);
	}

	@Override
	public double getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return String.format(
				"%s [id = %d, inout = %d/%d, value = %+.3f, activation = %s, output = %+.3f, delta = %+.3f]",
				this.getClass().getName(), getId(), incoming.size(), outgoing.size(), getValue(), activation.getName(),
				output, delta);
	}

}
