package bob.nn;

import java.util.List;
import java.util.Set;

/**
 * Trainiert ein einfaches Netzwerk aus jeweils einem Ein- und Ausgabeneuron
 * (ohne versteckte Schicht).
 * 
 * @author bobmin
 *
 */
public class SimpleTrainer {

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
	public SimpleTrainer(final Network network, final Training training) {
		this.network = network;
		this.training = training;
	}

	/**
	 * 
	 * @param m
	 *            die Lernrate
	 */
	public void doIt(final double m) {
		// die Eingabeschicht
		final List<InputNeuron> input = network.getInputLayer().getNeurons();
		// die Ausgabeschicht
		final List<WorkingNeuron> output = network.getOutputLayer().getNeurons();

		// die Trainigsdaten:
		// {
		// ---{
		// ------{ 0.1 }, = Eingangswerte
		// ------{ 0.0 } = erwartete Ausgabewerte
		// ---}
		// }
		final double[][][] data = training.getData();

		int loop = 0, auto = 0;
		double globalError;
		do {
			training.startLoop(loop);

			// die Summe der lokalen Fehler
			// geteilt durch die Anzahl der Trainingssätze
			globalError = 0.0;

			for (int trainIndex = 0; trainIndex < data.length; trainIndex++) {
				final double[] trainInputs = data[trainIndex][0];
				final double[] trainOutputs = data[trainIndex][1];

				// Eingabe setzen + Netzwerk berechnen
				network.activate(trainInputs);

				// Ausgabe prüfen + Gewichte aktualisieren
				for (int outputIndex = 0; outputIndex < output.size(); outputIndex++) {
					// aktuelles Ergebnis
					final double actual = output.get(outputIndex).getValue();
					// erwartetes Ergebnis
					final double y = trainOutputs[outputIndex];

					// Fehler in diesem Trainingsdatensatz
					final double localError = y - actual;
					// ...und über alle Trainingssätze
					globalError += localError;

					training.showData(loop, trainIndex, actual, localError);

					// Gewichte zur aktuellen Ausgabe neubesetzen
					final Neuron n = output.get(outputIndex);
					if (n instanceof WorkingNeuron) {
						final Set<Connection> connecttions = ((WorkingNeuron) n).getInputs();
						for (Connection c : connecttions) {
							final double value = c.getLeftNeuron().getValue();
							final double oldWeight = c.getWeight();
							final double newWeight = oldWeight + localError * m * value;
							c.setWeight(newWeight);
						}
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

}
