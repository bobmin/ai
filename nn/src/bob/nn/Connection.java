package bob.nn;

public class Connection {

	private final Neuron n;

	private double weight = Math.random();

	public Connection(final Neuron n) {
		this.n = n;
	}

	public Neuron getNeuron() {
		return n;
	}

	public void setWeight(final double value) {
		this.weight = value;
	}

	public double getWeight() {
		return weight;
	}

}
