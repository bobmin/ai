package bob.nn;

import java.util.ArrayList;
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
	private final List<WorkingLayer> hiddenLayers;

	/** die Ausgabeschicht */
	private final WorkingLayer outputLayer;

	/**
	 * Erstellt ein Netzwerk aus Eingabe- und Ausgabeschicht mit beliebig vielen
	 * versteckten Schichten. Sind die Bias-Einstellungen aktiviert, wird in der
	 * jeweiligen Schicht ein zusätzlicher Bias erzeugt.
	 * 
	 * @param inputCount
	 *            die Anzahl der Neuronen in der Eingabeschicht
	 * @param inputBias
	 *            die Bias-Option zur Einagbeschicht
	 * @param hiddenCount
	 *            die Anzahl und Neuronen in den versteckten Schichten; jeder
	 *            Index vom Array ist eine versteckte Schicht
	 * @param hiddenBias
	 *            die Bias-Option für die versteckten Schichten; ohne Angabe
	 *            wird kein Bias erzeugt
	 * @param outputCount
	 *            die Anzahl der Neuronen in der Ausgabeschicht
	 */
	public Network(final int inputCount, final boolean inputBias, final int[] hiddenCount, final boolean[] hiddenBias,
			final int outputCount) {
		if (1 > inputCount) {
			throw new IllegalArgumentException("inputCount < 1");
		}
		if (1 > outputCount) {
			throw new IllegalArgumentException("outputCount < 1");
		}

		// die Eingabeschicht
		inputLayer = new InputLayer(inputCount, inputBias);

		// die versteckten Schichten
		hiddenLayers = new ArrayList<WorkingLayer>(hiddenCount.length);
		for (int idx = 0; idx < hiddenCount.length; idx++) {
			final boolean bias = (idx >= hiddenBias.length ? false : hiddenBias[idx]);
			WorkingLayer x = new WorkingLayer(hiddenCount[idx], bias);
			if (0 == idx) {
				connect(inputLayer, x);
			} else {
				connect(hiddenLayers.get(idx - 1), x);
			}
			hiddenLayers.add(x);
		}

		// die Ausgabeschicht
		outputLayer = new WorkingLayer(outputCount, false);
		if (0 == hiddenCount.length) {
			connect(inputLayer, outputLayer);
		} else {
			connect(hiddenLayers.get(hiddenLayers.size() - 1), outputLayer);
		}
	}

	/**
	 * Verbindet jedes Neuronen von <code>left</code> mit jedem Neuron von
	 * <code>right</code>.
	 * 
	 * @param left
	 *            die Neuronen-Quelle
	 * @param right
	 *            das Neuronen-Ziel
	 */
	private void connect(final AbstractLayer leftLayer, final AbstractLayer rightLayer) {
		final List<Neuron> leftNeurons = leftLayer.getNeurons();
		final List<Neuron> rightNeurons = rightLayer.getNeurons();
		for (Neuron r : rightNeurons) {
			if (r instanceof WorkingNeuron) {
				for (Neuron l : leftNeurons) {
					((WorkingNeuron) r).putInput(l);
				}
				if (leftLayer.hasBias()) {
					((WorkingNeuron) r).putInput(leftLayer.getBias());
				}
			}
		}
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
	public List<WorkingLayer> getHiddenLayers() {
		return hiddenLayers;
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
	public void activate(final double[] inputValues) {
		// Eingabewerte setzen
		List<InputNeuron> inputNeurons = inputLayer.getNeurons();
		for (int inputIndex = 0; inputIndex < inputValues.length; inputIndex++) {
			final InputNeuron inputNeuron = inputNeurons.get(inputIndex);
			inputNeuron.setValue(inputValues[inputIndex]);
		}
		// alle Neuronen aktivieren
		for (WorkingLayer layer : hiddenLayers) {
			activateLayer(layer);
		}
		activateLayer(outputLayer);
	}

	private void activateLayer(final WorkingLayer layer) {
		for (Neuron n : layer.getNeurons()) {
			if (n instanceof WorkingNeuron) {
				((WorkingNeuron) n).activate();
			}
		}
	}

}
