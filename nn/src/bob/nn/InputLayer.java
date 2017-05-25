package bob.nn;

public class InputLayer extends AbstractLayer<InputNeuron> {

	public InputLayer(final int count, final boolean useBias) {
		super(count, InputNeuron.class, useBias);
	}

}
