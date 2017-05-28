package bob.nn.activation;

public class SigmoidActivation extends Activation {

	public SigmoidActivation() {
		super("SIGMOID");
	}

	@Override
	public double computeFunction(double value) {
		return (1.0 / (1.0 + Math.pow(Math.E, (-1.0 * value))));
	}

	@Override
	public double revertFunction(double value) {
		final double a = computeFunction(value);
		return (a * (1.0 - a));
	}

}
