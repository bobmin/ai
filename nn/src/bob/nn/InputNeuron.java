package bob.nn;

public class InputNeuron extends Neuron {

	private double value = 0.0;

	public InputNeuron() {
	}

	public void setValue(final double value) {
		this.value = value;
	}

	@Override
	public double getOutput() {
		return value;
	}

}
