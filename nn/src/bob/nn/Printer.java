package bob.nn;

import java.io.PrintStream;
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
		final List<InputNeuron> input = net.getInputLayer().getNeurons();
		for (InputNeuron n : input) {
			out.println("\t" + n);
		}
		if (net.getInputLayer().hasBias()) {
			print(out, "\t", net.getInputLayer().getBias());
		}
		// die versteckten Schichten
		out.println("HIDDEN:");
		List<WorkingLayer> hiddenMap = net.getHiddenLayers();
		for (int idx = 0; idx < hiddenMap.size(); idx++) {
			out.println("\tLAYER[" + idx + "]");
			final List<WorkingNeuron> neurons = hiddenMap.get(idx).getNeurons();
			for (Neuron n : neurons) {
				print(out, "\t\t", n);
			}
			if (hiddenMap.get(idx).hasBias()) {
				print(out, "\t\t", hiddenMap.get(idx).getBias());
			}
		}
		// die Ausgabeschicht
		out.println("OUTPUT:");
		final List<WorkingNeuron> output = net.getOutputLayer().getNeurons();
		for (Neuron n : output) {
			print(out, "\t", n);
		}
	}

	private void print(final PrintStream out, final String margin, final Neuron n) {
		out.println(margin + n);
		if (n instanceof WorkingNeuron) {
			final Set<Connection> connections = ((WorkingNeuron) n).getInputs();
			for (Connection c : connections) {
				out.println(margin + "\t" + c);
			}
		}
	}

}
