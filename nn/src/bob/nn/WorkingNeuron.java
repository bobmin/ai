package bob.nn;

import java.util.LinkedHashSet;
import java.util.Set;

public class WorkingNeuron extends Neuron {

	private final Set<Connection> connections = new LinkedHashSet<>();

	public WorkingNeuron() {
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
		return x;
	}

	public Set<Connection> getInput() {
		return connections;
	}

}
