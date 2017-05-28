package bob.demo;

import bob.nn.Network;
import bob.nn.NetworkBuilder;
import bob.nn.Printer;
import bob.nn.Training;

public abstract class AbstractDemo implements Training {

	/** die formatierte Konsolenausgabe des Netzwerks */
	protected final Printer printer = new Printer(System.out);

	/** das Netzwerk */
	protected final Network network;

	public AbstractDemo(final int inputCount, final boolean inputBias, final int[] hiddenCount,
			final boolean[] hiddenBias, final int outputCount) {
		final NetworkBuilder builder = new NetworkBuilder();
		builder.initInputLayer(inputCount, inputBias);
		builder.initHiddenLayers(hiddenCount.length);
		for (int idx = 0; idx < hiddenCount.length; idx++) {
			final boolean useBias = (idx < hiddenBias.length ? hiddenBias[idx] : false);
			builder.initHiddenLayer(hiddenCount[idx], useBias);
		}
		builder.initOutputLayer(outputCount);
		network = builder.createNetwork();
	}

}
