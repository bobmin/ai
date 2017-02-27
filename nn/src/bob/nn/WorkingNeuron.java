package bob.nn;

import java.util.LinkedList;
import java.util.List;

public class WorkingNeuron extends Neuron {

	private final List<Connection> l = new LinkedList<>();

	public WorkingNeuron() {
	}

	public void putInput(final Neuron n) {
		l.add(new Connection(n));
	}

	@Override
	public double getOutput() {
		double x = 0;
		for (Connection c : l) {
			x += (c.getWeight() * c.getNeuron().getOutput());
		}
		return x;
	}

}
