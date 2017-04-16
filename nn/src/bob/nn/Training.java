package bob.nn;

/**
 * Steuert das Training und informiert über dessen Verlauf.
 */
public interface Training {

	/**
	 * Liefert die Trainigsdaten.
	 * 
	 * @return ein Array, niemals <code>null</code>
	 */
	double[][][] getData();

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