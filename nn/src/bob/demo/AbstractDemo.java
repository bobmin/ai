package bob.demo;

import bob.nn.Network;
import bob.nn.Printer;
import bob.nn.Training;

public abstract class AbstractDemo implements Training {

	/** die formatierte Konsolenausgabe des Netzwerks */
	protected final Printer printer = new Printer(System.out);

	/** das Netzwerk */
	protected final Network network;

	public AbstractDemo(final int inputCount, final boolean inputBias, final int[] hiddenCount,
			final boolean[] hiddenBias, final int outputCount) {
		network = new Network(inputCount, inputBias, hiddenCount, hiddenBias, outputCount);
	}

}
