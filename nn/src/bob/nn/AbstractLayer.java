package bob.nn;

import java.util.ArrayList;
import java.util.List;

/**
 * Beschreibt/erstellt eine Schicht im neuronalen Netzwerk.
 * 
 * @author bobmin
 *
 * @param <T>
 */
public abstract class AbstractLayer<T extends AbstractNeuron> {

	protected final List<T> neurons;

	private BiasNeuron bias = null;

	public AbstractLayer(final int count, final Class<T> neuronClass, final boolean useBias) {
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		if (useBias) {
			this.bias = new BiasNeuron();
		}
		neurons = new ArrayList<>(count);
		for (int idx = 0; idx < count; idx++) {
			try {
				neurons.add(neuronClass.newInstance());
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	public List<T> getNeurons() {
		return neurons;
	}

	/**
	 * Liefert die Anzahl der Neuronen (ohne BIAS).
	 * 
	 * @return eine Zahl
	 */
	public int getSizeWithoutBias() {
		return neurons.size();
	}

	public T getNeuron(final int index) {
		return neurons.get(index);
	}

	public boolean hasBias() {
		return (null != bias);
	}

	public BiasNeuron getBias() {
		return bias;
	}

}
