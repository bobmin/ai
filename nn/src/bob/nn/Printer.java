package bob.nn;

import java.io.PrintStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Gibt ein Netzwerk formatiert aus.
 * 
 * @author bobmin
 *
 */
public class Printer {

	static {
		// Punkt statt Komma
		Locale.setDefault(Locale.ENGLISH);
	}

	/** der Trenner innerhalb der Konsole */
	private static final String SEPARATOR = "------------------------------------------------------------";

	/** das Ausgabeziel */
	private final PrintStream out;

	/**
	 * Instanziiert die Ausgabe.
	 * 
	 * @param out
	 *            das Ausgabeziel
	 */
	public Printer(final PrintStream out) {
		this.out = out;
	}

	public void separator() {
		out.println(SEPARATOR);
	}

	public void text(final String value) {
		out.println(value);
	}

	public void text(final String value, Object... args) {
		out.println(String.format(value, args));
	}

	public void print(final Network net) {
		// die Eingabeschicht
		out.println("INPUT:");
		if (net.getInputLayer().hasBias()) {
			print(out, "\t", net.getInputLayer().getBias());
		}
		final List<InputNeuron> input = net.getInputLayer().getNeurons();
		for (InputNeuron n : input) {
			out.println("\t" + n);
		}
		// die versteckten Schichten
		final Deque<WorkingLayer> hiddenMap = net.getHiddenLayers();
		int idx = 0;
		final Iterator<WorkingLayer> it = hiddenMap.iterator();
		while (it.hasNext()) {
			WorkingLayer hiddenLayer = it.next();
			out.println("HIDDEN[" + idx + "]:");
			if (hiddenLayer.hasBias()) {
				print(out, "\t", hiddenLayer.getBias());
			}
			final List<WorkingNeuron> neurons = hiddenLayer.getNeurons();
			for (AbstractNeuron n : neurons) {
				print(out, "\t", n);
			}
		}
		// die Ausgabeschicht
		out.println("OUTPUT:");
		final List<WorkingNeuron> output = net.getOutputLayer().getNeurons();
		for (AbstractNeuron n : output) {
			print(out, "\t", n);
		}
	}

	private void print(final PrintStream out, final String margin, final AbstractNeuron n) {
		out.println(margin + n);
		if (n instanceof WorkingNeuron) {
			final Set<Connection> connections = ((WorkingNeuron) n).getIncoming();
			for (Connection c : connections) {
				out.println(margin + "\t" + c);
			}
		}
	}

	public void showError(Network network, double... expected) {
		final WorkingLayer outputLayer = network.getOutputLayer();
		if (expected.length != outputLayer.getSizeWithoutBias()) {
			throw new IllegalArgumentException("[expected] are not correct");
		}

		// Ausgabeschicht
		out.println("id  val    f'     exp    out    d");
		final List<WorkingNeuron> neurons = outputLayer.getNeurons();
		double[] outputDelta = new double[neurons.size()];
		final int size = outputLayer.getSizeWithoutBias();
		for (int idx = 0; idx < expected.length; idx++) {
			final WorkingNeuron n = neurons.get(size - idx - 1);
			final double output = n.getOutput();
			final double r = n.getActivation().revertFunction(output);
			outputDelta[idx] = r * (expected[idx] - output);
			out.printf("%2d %6.3f %6.3f %6.3f %6.3f %6.3f%n", n.getId(), n.getValue(), r, expected[idx], output,
					outputDelta[idx]);
		}

		// versteckte Schichten
		out.println("id  val    f'");
		final Iterator<WorkingLayer> it = network.getHiddenLayers().descendingIterator();
		while (it.hasNext()) {
			final WorkingLayer layer = it.next();
			final List<WorkingNeuron> hiddenNeurons = layer.getNeurons();
			final int hiddenSize = hiddenNeurons.size();
			for (int idx = (hiddenSize - 1); idx >= 0; idx--) {
				final WorkingNeuron n = hiddenNeurons.get(idx);
				final double output = n.getOutput();
				final double r = n.getActivation().revertFunction(output);
				out.printf("%2d %6.3f %6.3f   exp    out    d%n", n.getId(), n.getValue(), r);
				final Set<Connection> outgoing = n.getOutgoing();
				int outputIndex = outputDelta.length - 1;
				double sumHiddenDelta = 0.0;
				for (Connection o : outgoing) {
					final double delta = outputDelta[outputIndex];
					final double hiddenDelta = delta * o.getWeight();
					out.printf("                  %6.3f %6.3f %6.3f%n", delta, o.getWeight(), hiddenDelta);
					sumHiddenDelta += hiddenDelta;
					outputIndex--;
				}
				out.printf("                       sum(d) = %6.3f%n", sumHiddenDelta);
				out.printf("                  f' * sum(d) = %6.3f%n", (r * sumHiddenDelta));
			}
		}
	}

}
