package bob.nn;

/**
 * Beim Eingabeneuron wird dessen Wert manuell gesetzt und es wird keine
 * Aktivierung angewendet.
 * 
 * @author bobmin
 *
 */
public class InputNeuron extends Neuron {

	/**
	 * Instanziiert das Objekt.
	 */
	public InputNeuron() {
	}

	/**
	 * Setzt den Wert vor Aktivierung.
	 * 
	 * @param value
	 *            der neue Wert vor Aktivierung
	 */
	public void setValue(final double newValue) {
		this.value = newValue;
	}

}
