package bob.nn;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import bob.nn.activation.Activation;
import bob.nn.activation.LinearActivation;

public class Network {

	static {
		// Punkt statt Komma
		Locale.setDefault(Locale.ENGLISH);
	}

	/** der Logger */
	private static final Logger LOG = Logger.getLogger(Network.class.getName());

	/** die Trainingsdaten */
	private static final double[][] TRAIN = { { 0.1, 0.0 }, { 0.2, 0.0 }, { 0.3, 0.0 }, { 0.4, 1.0 }, { 0.5, 1.0 },
			{ 0.6, 1.0 }, { 0.7, 1.0 }, { 0.8, 1.0 }, { 0.9, 1.0 } };

	/** learning rate */
	private static final double m = 0.1;

	/** bias */
	private static final double b = 1.0;

	public static void main(String[] args) {
		final Network network = new Network();

		network.createInput(1);
		network.createOutput(1, new LinearActivation());

		network.print(System.out);

		double globalError;
		int loop = 0, auto = 0;
		do {
			globalError = 0.0;
			for (int trainIndex = 0; trainIndex < TRAIN.length; trainIndex++) {
				final double x = TRAIN[trainIndex][0];
				network.setInput(0, x);
				final double[] result = network.getResult();
				for (int resultIndex = 0; resultIndex < result.length; resultIndex++) {
					final double actual = result[resultIndex];
					final double localError = TRAIN[trainIndex][1] - actual;
					globalError += localError;
					System.out.printf("TRAIN[%d][%d]: x = %.1f, actual = %.1f, error = %f%n", loop, trainIndex, x,
							actual, localError);
					final Set<Connection> connecttions = network.output[resultIndex].getInput();
					for (Connection c : connecttions) {
						final double oldWeight = c.getWeight();
						final double newWeight = oldWeight + localError * m * x;
						c.setWeight(newWeight);
					}
				}
			}
			globalError = globalError / TRAIN.length;
			System.out.printf("global error: %f%n", globalError);
			System.out.println("------------------------------------------------------------");
			if (loop >= auto) {
				System.out.print("(p)rint, (a)uto, (t)est, (e)xit: ");
				final Scanner keyboard = new Scanner(System.in);
				final String cmd = keyboard.nextLine();
				if ("p".equals(cmd)) {
					network.print(System.out);
				} else if ("a".equals(cmd)) {
					System.out.print("lines: ");
					final int lines = keyboard.nextInt();
					auto = loop + lines;
				} else if ("t".equals(cmd)) {
					String testLine = null;
					do {
						System.out.print("input or (q)uit: ");
						testLine = keyboard.nextLine();
						if (testLine.matches("[\\d\\.]+")) {
							network.setInput(0, Double.parseDouble(testLine));
							System.out.println(testLine + " --> " + Arrays.toString(network.getResult()));
						}
					} while (!"q".equals(testLine));
				} else if ("e".equals(cmd)) {
					System.exit(0);
				}
			}
			loop++;
		} while (globalError > 0.1 && loop < 1000);

	}

	private InputNeuron[] input;

	private Map<Integer, WorkingNeuron[]> hiddenMap = new LinkedHashMap<>();

	private WorkingNeuron[] output;

	public Network() {
	}

	/**
	 * Erstellt die Eingaben.
	 * 
	 * @param count
	 *            die Anzahl der Eingaben
	 */
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
		// Verbindungen erstellen
		connect(newNeurons);
		// Layer ablegen
		hiddenMap.put(Integer.valueOf(hiddenMap.size()), newNeurons);
	}

	public void createOutput(final int count, final Activation fkt) {
		output = new WorkingNeuron[count];
		for (int idx = 0; idx < count; idx++) {
			output[idx] = new WorkingNeuron();
			output[idx].setActivation(fkt);
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
				print(out, "\t\t", n);
			}
		}
		out.println("OUTPUT:");
		for (WorkingNeuron n : output) {
			print(out, "\t", n);
		}
	}

	private void print(final PrintStream out, final String margin, final WorkingNeuron n) {
		out.println(margin + n);
		final Set<Connection> connections = n.getInput();
		for (Connection c : connections) {
			out.println(margin + "\t" + c);
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

}
