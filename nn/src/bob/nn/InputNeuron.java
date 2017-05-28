package bob.nn;

/**
 * Beim Eingabeneuron wird dessen Wert manuell gesetzt und es wird keine
 * Aktivierung angewendet.
 * 
 * @author bobmin
 *
 */
public class InputNeuron extends AbstractNeuron {

	/**
	 * Instanziiert das Objekt.
	 */
	public InputNeuron() {
	}

	/**
	 * Setzt den Wert für das Neuron. Eingangsneuronen werden nicht aktiviert.
	 * Der Wert entspricht auch der Ausgabe.
	 * 
	 * @param value
	 *            der Wert vom Neuron
	 */
	public void setValue(final double newValue) {
		this.value = newValue;
	}

	@Override
	public double getOutput() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s [id = %d, out = %d, value = %.3f]", this.getClass().getName(), getId(),
				outgoing.size(), value);
	}

}
