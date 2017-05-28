package bob.demo;

import java.util.List;
import java.util.Scanner;

import bob.nn.InputLayer;
import bob.nn.SimpleTrainer;
import bob.nn.WorkingNeuron;

/**
 * Erstellt ein einfaches Netzwerk und ermöglicht dessen Test.
 * 
 * @author bobmin
 *
 */
public class SimpleDemo extends AbstractDemo {

	/** die Trainingsdaten */
	// @formatter:off
	private static final double[][][] TRAIN = { 
			{ { 0.1 }, { 0.0 } }, 
			{ { 0.2 }, { 0.0 } }, 
			{ { 0.3 }, { 0.0 } }, 
			{ { 0.4 }, { 1.0 } }, 
			{ { 0.5 }, { 1.0 } },
			{ { 0.6 }, { 1.0 } }, 
			{ { 0.7 }, { 1.0 } }, 
			{ { 0.8 }, { 1.0 } }, 
			{ { 0.9 }, { 1.0 } } 
	};
	// formatter:on

	/** learning rate */
	private static final double m = 0.7;

	/** bias */
	private static final double b = 1.0;

	/** das Training */
	private final SimpleTrainer trainer;

	/**
	 * die Konsoleneingabe
	 * 
	 * @formatter:off
	 * http://stackoverflow.com/questions/27286690/in-java-is-it-possible-to-re-open-system-in-after-closing-it
	 * http://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-nextint-or-other-nextfoo
	 * @formatter:on
	 */
	private Scanner scanner = null;

	/**
	 * Startet die Anwendung.
	 * 
	 * @param args
	 *            die Programmparameter
	 */
	public static void main(String[] args) {
		new SimpleDemo();
	}

	/**
	 * Instanziiert eine einfache Demo zu den verschiedenen Klassen im Paket.
	 */
	private SimpleDemo() {
		super(1, false, new int[] {}, new boolean[] {}, 1);

		printer.text("Ein Eingabe- und ein Ausgabeneuron lernen ab 0.4 den Zustand \"ja\" einzunehmen.");
		printer.text("Das \"ja\" entspricht einem Ausgangspegel von 1.");
		printer.text("Zuerst startet die Lernphase. Im Anschluss kann das Netz befragt werden.");
		printer.separator();

		printer.print(network);

		scanner = new Scanner(System.in);

		trainer = new SimpleTrainer(network, this);
		trainer.doIt(m);

		scanner.close();

		printer.separator();
		System.out.println("BYE!");

	}

	/**
	 * Liefert eine Zeilenzahl vom Benutzer, die per Konsoleneingabe erfragt
	 * wird.
	 * 
	 * @return eine Zeichenkette oder <code>null</code>
	 */
	private int frageBenutzer() {
		printer.separator();
		printer.text("lines, (p)rint, (t)est, (e)xit: ");

		int antwort = 0;

		final String cmd = scanner.nextLine();

		if (cmd.matches("[\\d\\.]+")) {
			antwort = Integer.parseInt(cmd);
		} else if ("p".equals(cmd)) {
			printer.print(network);
		} else if ("t".equals(cmd)) {
			testeNetzwerk();
		} else if ("e".equals(cmd)) {
			antwort = -1;
		}

		return antwort;
	}

	private void testeNetzwerk() {
		printer.text("input or (e)xit: ");
		String testLine = null;

		final InputLayer inputLayer = network.getInputLayer();
		final int inputLenght = inputLayer.getNeurons().size();

		int intputIndex = 0;
		double[] inputValues = new double[inputLenght];

		do {
			printer.separator();
			printer.text(String.format("Input[%d]: ", intputIndex));
			testLine = scanner.nextLine();

			if (testLine.matches("[\\d\\.]+")) {
				inputValues[intputIndex] = Double.parseDouble(testLine);
				intputIndex++;
			}

			if (intputIndex >= inputLenght) {
				// rechnen
				network.forward(inputValues);
				// ausgeben
				final List<WorkingNeuron> outputNeurons = network.getOutputLayer().getNeurons();
				for (int outputIndex = 0; outputIndex < outputNeurons.size(); outputIndex++) {
					final double outputValue = outputNeurons.get(outputIndex).getValue();
					printer.text(String.format("Output[%d] = %f", outputIndex, outputValue));
				}
				// neustarten
				intputIndex = 0;
			}

		} while (!"e".equals(testLine));
	}

	@Override
	public double[][][] getData() {
		return TRAIN;
	}

	@Override
	public void startLoop(int loop) {
		printer.separator();
	}

	@Override
	public void showData(final int loop, final int index, final double actual, final double localError) {
		printer.text("TRAIN[%d][%d]: x = %.1f, y = %.1f, actual = %.3f, error = %f%n", loop, index, TRAIN[index][0][0],
				TRAIN[index][1][0], actual, localError);
	}

	@Override
	public void finishLoop(final int loop, final double globalError) {
		printer.separator();
		System.out.printf("global error: %f%n", globalError);
	}

	@Override
	public int getAutoLines(final double globalError) {
		return frageBenutzer();
	}

	@Override
	public double getGlobalErrorStop() {
		return 0.05;
	}

	@Override
	public void stopTrainer() {
		printer.separator();
		printer.print(network);
		printer.separator();
		testeNetzwerk();
	}

}
