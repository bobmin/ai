package bob.nn;

import java.util.Set;

/**
 * Trainiert ein Netzwerk.
 * 
 * @author bobmin
 *
 */
public class Trainer {

	/** das Netzwerk wird trainiert */
	private final Network network;

	/** das Training */
	private final Training training;

	/**
	 * Instanziiert den Trainer für das Netzwerk.
	 * 
	 * @param network
	 *            das Netzwerk
	 * @param training
	 *            das Training
	 */
	public Trainer(final Network network, final Training training) {
		this.network = network;
		this.training = training;
	}

	/**
	 * 
	 * @param m
	 *            die Lernrate
	 */
	public void doIt(final double m) {
		// die Trainigsdaten
		final double[][] data = training.getData();

		// die Ausgabeschicht
		final WorkingNeuron[] output = network.getOutputNeurons();

		int loop = 0, auto = 0;
		double globalError;
		do {
			training.startLoop(loop);

			globalError = 0.0;
			for (int trainIndex = 0; trainIndex < data.length; trainIndex++) {
				final double x = data[trainIndex][0];
				network.setInput(0, x);
				final double[] result = network.getResult();
				for (int resultIndex = 0; resultIndex < result.length; resultIndex++) {
					final double actual = result[resultIndex];
					final double y = data[trainIndex][1];
					final double localError = y - actual;
					globalError += localError;

					training.showData(loop, trainIndex, actual, localError);

					final Set<Connection> connecttions = output[resultIndex].getInput();

					for (Connection c : connecttions) {
						final double oldWeight = c.getWeight();
						final double newWeight = oldWeight + localError * m * x;
						c.setWeight(newWeight);
					}
				}
			}
			globalError = globalError / data.length;

			training.finishLoop(loop, globalError);

			if (loop >= auto) {
				final int lines = training.getAutoLines(globalError);
				auto = loop + lines;
			}

			loop++;

		} while (globalError > training.getGlobalErrorStop());

		training.stopTrainer();

	}

	/**
	 * Informiert über den Trainingsablauf.
	 */
	public interface Training {

		/**
		 * Liefert die Trainigsdaten.
		 * 
		 * @return ein Array, niemals <code>null</code>
		 */
		double[][] getData();

		void startLoop(int loop);

		void showData(final int loop, final int index, final double actual, final double localError);

		void finishLoop(final int loop, final double globalError);

		/**
		 * Liefert die Anzahl der Zeilen, die automatisch ohne Zwischenstopp
		 * trainiert werden soll.
		 * 
		 * @param globalError
		 *            der aktuelle Fehler
		 * @return eine Zahl >= 0
		 */
		int getAutoLines(final double globalError);

		/**
		 * Liefert die Abbruchbedingung zum Training. Ist der globale Fehler
		 * kleiner, wird das Training beendet.
		 * 
		 * @return ein Zahl
		 */
		double getGlobalErrorStop();

		void stopTrainer();

	}

}
