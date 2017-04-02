package bob.nn;

import java.util.LinkedList;
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

	/** die Eingabeschicht */
	private final InputNeuron[] input;

	/** die versteckten Schichten: Index = Schichtnummer */
	private final List<WorkingNeuron[]> hidden;

	/** die Ausgabeschicht */
	private final WorkingNeuron[] output;

	/**
	 * Erstellt ein Netzwerk aus Eingabe- und Ausgabeschicht mit beliebig vielen
	 * versteckten Schichten.
	 * 
	 * @param inputCount
	 *            die Anzahl der Neuronen in der Eingabeschicht
	 * @param hiddenCount
	 *            die Anzahl und Neuronen in den versteckten Schichten; jeder
	 *            Index vom Array ist eine versteckte Schicht
	 * @param outputCount
	 *            die Anzahl der Neuronen in der Ausgabeschicht
	 */
	public Network(final int inputCount, final int[] hiddenCount, final int outputCount) {
		if (1 > inputCount) {
			throw new IllegalArgumentException("inputCount < 1");
		}
		if (1 > outputCount) {
			throw new IllegalArgumentException("outputCount < 1");
		}
		// die Eingabeschicht
		input = createInputLayer(inputCount);
		// die versteckten Schichten
		hidden = new LinkedList<>();
		for (int idx = 0; idx < hiddenCount.length; idx++) {
			WorkingNeuron[] x = createWorkingNeurons(hiddenCount[idx]);
			if (0 == idx) {
				connect(input, x);
			} else {
				WorkingNeuron[] left = hidden.get(idx - 1);
				connect(left, x);
			}
			hidden.add(x);
		}
		// die Ausgabeschicht
		output = createWorkingNeurons(outputCount);
		if (0 == hiddenCount.length) {
			connect(input, output);
		} else {
			connect(hidden.get(hidden.size() - 1), output);
		}
	}

	/**
	 * Erstellt die Eingabeschicht.
	 * 
	 * @param count
	 *            die Anzahl der Neuronen
	 * @return ein Array, niemals <code>null</code>
	 */
	private InputNeuron[] createInputLayer(final int count) {
		InputNeuron[] x = new InputNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			x[idx] = new InputNeuron();
		}
		return x;
	}

	/**
	 * Erstellt eine Schicht mit Neuronen die zur Ausgabe oder als versteckte
	 * Schicht genutz werden kann.
	 * 
	 * @param count
	 *            die Anzahl der Neuronen
	 * @return ein Array, niemals <code>null</code>
	 */
	private WorkingNeuron[] createWorkingNeurons(final int count) {
		WorkingNeuron[] x = new WorkingNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			x[idx] = new WorkingNeuron();
		}
		return x;
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
	private void connect(final Neuron[] left, final WorkingNeuron[] right) {
		for (Neuron l : left) {
			for (WorkingNeuron r : right) {
				r.putInput(l);
			}
		}
	}

	/**
	 * Setzt den aktuellen Wert eines Eingabeneurons.
	 * 
	 * @param index
	 *            der Index vom Neuron innerhalb der Eingabeschicht
	 * @param value
	 *            der Wert
	 */
	public void setInput(final int index, final double value) {
		if (index < 0 || index >= input.length) {
			throw new IllegalArgumentException("index out auf range");
		}
		input[index].setValue(value);
	}

	/**
	 * Liefert die Eingabeschicht.
	 * 
	 * @return ein Array, niemals <code>null</code>
	 */
	public InputNeuron[] getInputNeurons() {
		return input;
	}

	/**
	 * Liefert die versteckten Schichten vom Netzwerk. Wurden keine versteckten
	 * Neuronen definiert, wird eine leere Map geliefert.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	public List<WorkingNeuron[]> getHiddenNeurons() {
		return hidden;
	}

	/**
	 * Liefert die Neuronen der Ausgabeschicht.
	 * 
	 * @return ein Array, niemals <code>null</code>
	 */
	public WorkingNeuron[] getOutputNeurons() {
		return output;
	}

}
