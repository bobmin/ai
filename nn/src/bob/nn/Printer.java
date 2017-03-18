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

	public void print(final Network net) {
		// die Eingabeschicht
		out.println("INPUT:");
		final InputNeuron[] input = net.getInputNeurons();
		for (InputNeuron n : input) {
			out.println("\t" + n);
		}
		// die versteckten Schichten
		out.println("HIDDEN:");
		List<WorkingNeuron[]> hiddenMap = net.getHiddenNeurons();
		for (int idx = 0; idx < hiddenMap.size(); idx++) {
			out.println("\tLAYER[" + idx + "]");
			final WorkingNeuron[] neurons = hiddenMap.get(idx);
			for (WorkingNeuron n : neurons) {
				print(out, "\t\t", n);
			}
		}
		// die Ausgabeschicht
		out.println("OUTPUT:");
		final WorkingNeuron[] output = net.getOutputNeurons();
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

}
