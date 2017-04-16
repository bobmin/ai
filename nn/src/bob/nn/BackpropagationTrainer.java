package bob.nn;

public class BackpropagationTrainer {

	/** das Netzwerk wird trainiert */
	private final Network network;

	/** das Training */
	private final Training training;

	public BackpropagationTrainer(final Network network, final Training training) {
		this.network = network;
		this.training = training;
	}

	public void doIt() {
		final double[][][] data = training.getData();

		for (double[][] d : data) {

			network.activate(d[0]);

			backwardPass();

		}

		training.stopTrainer();

	}

	private void backwardPass() {
	}

}
