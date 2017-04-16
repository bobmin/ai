package bob.nn;

import java.util.ArrayList;
import java.util.List;

public class Layer<T extends Neuron> {

	private final List<T> neurons;

	private BiasNeuron bias = null;

	public Layer(final int count, Class<T> neuronClass, final boolean useBias) {
		neurons = new ArrayList<>(count);
		for (int idx = 0; idx < count; idx++) {
			try {
				neurons.add(neuronClass.newInstance());
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalStateException(ex);
			}
		}
		if (useBias) {
			bias = new BiasNeuron();
		}
	}

	public List<T> getNeurons() {
		return neurons;
	}

	public boolean hasBias() {
		return (null != bias);
	}

	public BiasNeuron getBias() {
		return bias;
	}

}
