package bob.demo;

import java.util.Scanner;

import bob.nn.Network;
import bob.nn.Printer;
import bob.nn.Trainer;
import bob.nn.Trainer.Training;
import bob.nn.WorkingNeuron;

/**
 * Erstellt ein einfaches Netzwerk und ermöglicht dessen Test.
 * 
 * @author bobmin
 *
 */
public class SimpleDemo implements Training {

	/** die Trainingsdaten */
	private static final double[][] TRAIN = { { 0.1, 0.0 }, { 0.2, 0.0 }, { 0.3, 0.0 }, { 0.4, 1.0 }, { 0.5, 1.0 },
			{ 0.6, 1.0 }, { 0.7, 1.0 }, { 0.8, 1.0 }, { 0.9, 1.0 } };

	/** learning rate */
	private static final double m = 0.7;

	/** bias */
	private static final double b = 1.0;

	/** der Trenner innerhalb der Konsole */
	private static final String SEPARATOR = "------------------------------------------------------------";

	/** die formatierte Konsolenausgabe des Netzwerks */
	private final Printer printer;

	/** das Netzwerk */
	private final Network network;

	/** das Training */
	private final Trainer trainer;

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
		network = new Network(1, new int[] {}, 1);

		printer = new Printer(System.out);
		printer.print(network);

		scanner = new Scanner(System.in);

		trainer = new Trainer(network, this);
		trainer.doIt(m);

		scanner.close();

		System.out.println(SEPARATOR);
		System.out.println("BYE!");

	}

	/**
	 * Liefert eine Zeilenzahl vom Benutzer, die per Konsoleneingabe erfragt
	 * wird.
	 * 
	 * @return eine Zeichenkette oder <code>null</code>
	 */
	private int frageBenutzer() {
		System.out.println(SEPARATOR);

		System.out.print("lines, (p)rint, (t)est, (e)xit: ");

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
		System.out.println("input or (e)xit: ");
		String testLine = null;
		int intputIndex = 0;
		do {
			System.out.println(SEPARATOR);
			System.out.print(String.format("Input[%d]: ", intputIndex));
			testLine = scanner.nextLine();

			if (testLine.matches("[\\d\\.]+")) {
				network.setInput(intputIndex, Double.parseDouble(testLine));
				intputIndex++;
			}

			if (intputIndex >= network.getInputNeurons().length) {
				final WorkingNeuron[] outputNeurons = network.getOutputNeurons();
				for (int outputIndex = 0; outputIndex < outputNeurons.length; outputIndex++) {
					final double outputValue = outputNeurons[outputIndex].getOutput();
					System.out.println(String.format("Output[%d] = %f", outputIndex, outputValue));
				}
				intputIndex = 0;
			}

		} while (!"e".equals(testLine));
	}

	@Override
	public double[][] getData() {
		return TRAIN;
	}

	@Override
	public void startLoop(int loop) {
		System.out.println(SEPARATOR);
	}

	@Override
	public void showData(final int loop, final int index, final double actual, final double localError) {
		System.out.printf("TRAIN[%d][%d]: x = %.1f, y = %.1f, actual = %.3f, error = %f%n", loop, index,
				TRAIN[index][0], TRAIN[index][1], actual, localError);
	}

	@Override
	public void finishLoop(final int loop, final double globalError) {
		System.out.println(SEPARATOR);
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
		System.out.println(SEPARATOR);
		printer.print(network);
		System.out.println(SEPARATOR);
		testeNetzwerk();
	}

}
