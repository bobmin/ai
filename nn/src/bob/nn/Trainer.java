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
		// die Trainigsdaten:
		// [[0.1, 0.0], [0.2, 0.0], [0.3, 0.0]]
		// Eingabewert + erwarteter Ausgabewert
		final double[][] data = training.getData();

		// die Ausgabeschicht
		final WorkingNeuron[] output = network.getOutputNeurons();

		int loop = 0, auto = 0;
		double globalError;
		do {
			training.startLoop(loop);

			// die Summe der lokalen Fehler
			// geteilt durch die Anzahl der Trainingssätze
			globalError = 0.0;

			for (int trainIndex = 0; trainIndex < data.length; trainIndex++) {
				// Eingabe setzen
				final double x = data[trainIndex][0];
				network.setInput(0, x);

				for (int outputIndex = 0; outputIndex < output.length; outputIndex++) {
					// aktuelles Ergebnis
					final double actual = output[outputIndex].getOutput();
					// erwartetes Ergebnis
					final double y = data[trainIndex][1];

					// Fehler in diesem Trainingsdatensatz
					final double localError = y - actual;
					// ...und über alle Trainingssätze
					globalError += localError;

					training.showData(loop, trainIndex, actual, localError);

					// Gewichte zur aktuellen Ausgabe neubesetzen
					final Set<Connection> connecttions = output[outputIndex].getInput();
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
				if (-1 < lines) {
					auto = loop + lines;
				} else {
					auto = -1;
				}
			}

			loop++;

		} while (globalError > training.getGlobalErrorStop() && auto > -1);

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

		/**
		 * Signalisiert das Trainingsende.
		 */
		void stopTrainer();

	}

}
