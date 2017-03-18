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
	 * Erstellt das Netzwerk.
	 * 
	 * @param inputCount
	 *            die Anzahl der Neuronen in der Eingabeschicht
	 * @param hiddenCount
	 *            die Anzahl und Neuronen in den versteckten Schichten
	 * @param outputCount
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

	private WorkingNeuron[] createWorkingNeurons(final int count) {
		WorkingNeuron[] x = new WorkingNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			x[idx] = new WorkingNeuron();
		}
		return x;
	}

	private void connect(final Neuron[] left, final WorkingNeuron[] right) {
		for (Neuron l : left) {
			for (WorkingNeuron r : right) {
				r.putInput(l);
			}
		}
	}

	public void setInput(final int index, final double value) {
		if (index < 0 || index >= input.length) {
			throw new IllegalArgumentException("index out auf range");
		}
		input[index].setValue(value);
	}

	public double[] getResult() {
		final double[] result = new double[output.length];
		for (int idx = 0; idx < output.length; idx++) {
			result[idx] = output[idx].getOutput();
		}
		return result;
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
