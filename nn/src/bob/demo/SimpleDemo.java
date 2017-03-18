package bob.demo;

import java.util.Arrays;
import java.util.Scanner;

import bob.nn.Network;
import bob.nn.Printer;
import bob.nn.Trainer;
import bob.nn.Trainer.Training;

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
	private static final double m = 0.1;

	/** bias */
	private static final double b = 1.0;

	private final Printer printer;

	private final Network network;

	private final Trainer trainer;

	public static void main(String[] args) {
		new SimpleDemo();
	}

	public SimpleDemo() {
		network = new Network(1, new int[] {}, 1);

		printer = new Printer(System.out);
		printer.print(network);

		trainer = new Trainer(network, this);
		trainer.doIt(m);

	}

	@Override
	public double[][] getData() {
		return TRAIN;
	}

	@Override
	public void startLoop(int loop) {
		System.out.println("------------------------------------------------------------");
	}

	@Override
	public void showData(final int loop, final int index, final double actual, final double localError) {
		System.out.printf("TRAIN[%d][%d]: x = %.1f, y = %.1f, actual = %.3f, error = %f%n", loop, index,
				TRAIN[index][0], TRAIN[index][1], actual, localError);
	}

	@Override
	public void finishLoop(final int loop, final double globalError) {
		System.out.println("------------------------------------------------------------");
		System.out.printf("global error: %f%n", globalError);
	}

	@Override
	public int getAutoLines(final double globalError) {
		int autoLines = 0;
		System.out.println("------------------------------------------------------------");
		System.out.print("(p)rint, (a)uto, (t)est, (e)xit: ");
		final Scanner keyboard = new Scanner(System.in);
		final String cmd = keyboard.nextLine();
		if ("p".equals(cmd)) {
			printer.print(network);
		} else if ("a".equals(cmd)) {
			System.out.print("lines: ");
			autoLines = keyboard.nextInt();
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
		return autoLines;
	}

	@Override
	public double getGlobalErrorStop() {
		return 0.05;
	}

	@Override
	public void stopTrainer() {
		System.out.println("------------------------------------------------------------");
		printer.print(network);
	}

}
