package bob.nn;

import java.util.LinkedHashSet;
import java.util.Set;

import bob.nn.activation.Activation;
import bob.nn.activation.LinearActivation;

public class WorkingNeuron extends Neuron {

	private final Set<Connection> connections = new LinkedHashSet<>();

	private Activation activation = new LinearActivation();

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

	public void putInput(final Neuron n) {
		connections.add(new Connection(n));
	}

	@Override
	public double getOutput() {
		double x = 0;
		for (Connection c : connections) {
			x += (c.getWeight() * c.getNeuron().getOutput());
		}
		return activation.computeFunction(x);
	}

	public Set<Connection> getInput() {
		return connections;
	}

	@Override
	public String toString() {
		return String.format("%s [id = %d, activation = %s]", this.getClass().getName(), getId(), activation.getName());
	}

}
