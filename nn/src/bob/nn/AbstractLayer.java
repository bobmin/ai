package bob.nn;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLayer<T extends Neuron> {

	private final List<T> neurons;

	private BiasNeuron bias = null;

	public AbstractLayer(final int count, final Class<T> neuronClass, final boolean useBias) {
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		neurons = new ArrayList<>(count);
		for (int idx = 0; idx < count; idx++) {
			try {
				neurons.add(neuronClass.newInstance());
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalStateException(ex);
			}
		}
		if (useBias) {
			this.bias = new BiasNeuron();
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
