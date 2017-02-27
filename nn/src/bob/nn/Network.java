package bob.nn;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Network {

	public static void main(String[] args) {
		final Network network = new Network();

		network.createInput(2);
		network.createHidden(3);
		network.createOutput(1);

		network.print(System.out);

		network.setInput(0, 1);
		network.setInput(1, 1);
		final double[] result = network.getOutput();
		System.out.println("result: " + result);
	}

	private InputNeuron[] input;

	private Map<Integer, WorkingNeuron[]> hiddenMap = new LinkedHashMap<>();

	private WorkingNeuron[] output;

	public Network() {
	}

	public void createInput(final int count) {
		this.input = new InputNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			this.input[idx] = new InputNeuron();
		}
	}

	private void connect(final WorkingNeuron[] rightNeurons) {
		final Neuron[] leftNeurons;
		if (hiddenMap.isEmpty()) {
			leftNeurons = input;
		} else {
			leftNeurons = hiddenMap.get(Integer.valueOf(hiddenMap.size() - 1));
		}
		for (Neuron left : leftNeurons) {
			for (WorkingNeuron right : rightNeurons) {
				right.putInput(left);
			}
		}
	}

	public void createHidden(final int count) {
		// neue Neuronen
		final WorkingNeuron[] newNeurons = new WorkingNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			newNeurons[idx] = new WorkingNeuron();
		}
		hiddenMap.put(Integer.valueOf(hiddenMap.size()), newNeurons);
		// Verbindungen erstellen
		connect(newNeurons);
	}

	public void createOutput(final int count) {
		output = new WorkingNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			output[idx] = new WorkingNeuron();
		}
		connect(output);
	}

	public void print(final PrintStream out) {
		out.println("INPUT:");
		for (InputNeuron n : input) {
			out.println("\t" + n);
		}
		out.println("HIDDEN:");
		for (Entry<Integer, WorkingNeuron[]> e : hiddenMap.entrySet()) {
			out.println("\tLAYER[" + e.getKey().intValue() + "]");
			for (WorkingNeuron n : e.getValue()) {
				out.println("\t\t" + n);
			}
		}
		out.println("OUTPUT:");
		for (WorkingNeuron n : output) {
			out.println("\t" + n);
		}
	}

	public void setInput(final int index, final double value) {
		input[index].setValue(value);
	}

	public double[] getOutput() {
		final double[] result = new double[output.length];
		for (int idx = 0; idx < output.length; idx++) {
			result[idx] = output[idx].getOutput();
		}
		return result;
	}

}
