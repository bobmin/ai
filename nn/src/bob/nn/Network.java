package bob.nn;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Ein Netzwerk hat eine Eingabeschicht, eine Ausgabeschicht und optional
 * versteckte Schichten.
 * 
 * @author bobmin
 *
 */
public class Network {

	/** der Logger */
	private static final Logger LOG = Logger.getLogger(Network.class.getName());

	/** die Neuronen der Eingabeschicht */
	private final InputLayer inputLayer;

	/** die versteckten Schichten: Index = Schichtnummer */
	private final Deque<WorkingLayer> hiddenLayers;

	/** die Ausgabeschicht */
	private final WorkingLayer outputLayer;

	public Network(final InputLayer inputLayer, final Deque<WorkingLayer> hiddenLayers,
			final WorkingLayer outputLayer) {
		this.inputLayer = inputLayer;
		this.hiddenLayers = hiddenLayers;
		this.outputLayer = outputLayer;
	}

	/**
	 * Liefert die Eingabeschicht.
	 * 
	 * @return ein Array, niemals <code>null</code>
	 */
	public InputLayer getInputLayer() {
		return inputLayer;
	}

	/**
	 * Liefert die versteckten Schichten vom Netzwerk. Wurden keine versteckten
	 * Neuronen definiert, wird eine leere Map geliefert.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	public Deque<WorkingLayer> getHiddenLayers() {
		return hiddenLayers;
	}

	public WorkingLayer getHiddenLayer(final int index) {
		return (WorkingLayer) hiddenLayers.toArray()[index];
	}

	/**
	 * Liefert die Neuronen der Ausgabeschicht.
	 * 
	 * @return ein Array, niemals <code>null</code>
	 */
	public WorkingLayer getOutputLayer() {
		return outputLayer;
	}

	/**
	 * Setzt die aktuellen Werte für die Eingabeschicht und aktiviert das
	 * gesamte Netzwerk.
	 * 
	 * @param values
	 *            die neuen Eingabewerte
	 */
	public void forward(final double... inputValues) {
		if (inputValues.length != inputLayer.getSizeWithoutBias()) {
			throw new IllegalArgumentException("[inputValues] are not correct");
		}
		// Eingabewerte setzen
		for (int inputIndex = 0; inputIndex < inputValues.length; inputIndex++) {
			inputLayer.getNeuron(inputIndex).setValue(inputValues[inputIndex]);
		}
		// alle Neuronen aktivieren
		final Iterator<WorkingLayer> it = hiddenLayers.iterator();
		while (it.hasNext()) {
			final WorkingLayer layer = it.next();
			layer.activate();
		}
		outputLayer.activate();
	}

	public void backward(double... expected) {
		// Ausgabeschicht
		if (expected.length != outputLayer.getSizeWithoutBias()) {
			throw new IllegalArgumentException("[expected] are not correct");
		}
		final List<WorkingNeuron> neurons = outputLayer.getNeurons();
		final int size = outputLayer.getSizeWithoutBias();
		for (int idx = 0; idx < expected.length; idx++) {
			final WorkingNeuron n = neurons.get(size - idx - 1);
			final double output = n.getOutput();
			final double r = n.getActivation().revertFunction(output);
			final double d = r * (expected[idx] - output);
			n.adjustIncomingWeights(d);
		}
		// versteckte Schichten
		final Iterator<WorkingLayer> it = hiddenLayers.descendingIterator();
		while (it.hasNext()) {
			final WorkingLayer layer = it.next();

		}
	}

}
