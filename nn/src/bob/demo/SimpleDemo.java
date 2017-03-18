package bob.demo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import bob.nn.Connection;
import bob.nn.Network;
import bob.nn.Printer;
import bob.nn.WorkingNeuron;

/**
 * Erstellt ein einfaches Netzwerk und ermöglicht dessen Test.
 * 
 * @author bobmin
 *
 */
public class SimpleDemo {

	/** die Trainingsdaten */
	private static final double[][] TRAIN = { { 0.1, 0.0 }, { 0.2, 0.0 }, { 0.3, 0.0 }, { 0.4, 1.0 }, { 0.5, 1.0 },
			{ 0.6, 1.0 }, { 0.7, 1.0 }, { 0.8, 1.0 }, { 0.9, 1.0 } };

	/** learning rate */
	private static final double m = 0.1;

	/** bias */
	private static final double b = 1.0;

	public static void main(String[] args) {
		final Network network = new Network(1, new int[] {}, 1);
		final WorkingNeuron[] output = network.getOutputNeurons();

		final Printer printer = new Printer(System.out);
		printer.print(network);

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
					final double y = TRAIN[trainIndex][1];
					final double localError = y - actual;
					globalError += localError;
					System.out.printf("TRAIN[%d][%d]: x = %.1f, y = %.1f, actual = %.3f, error = %f%n", loop,
							trainIndex, x, y, actual, localError);

					final Set<Connection> connecttions = output[resultIndex].getInput();

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
					printer.print(network);
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
		} while (globalError > 0.05 && loop < 1000);

	}

}
