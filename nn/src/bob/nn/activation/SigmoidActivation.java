package bob.nn.activation;

public class SigmoidActivation extends Activation {

	public SigmoidActivation() {
		super("SIGMOID");
	}

	@Override
	public double computeFunction(double value) {
		return (1 / (1 + Math.pow(Math.E, (-1 * value))));
	}

}
