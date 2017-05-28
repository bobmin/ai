package bob.nn;

/**
 * Ein Bias hat immer den Wert 1.
 * 
 * @author bobmin
 *
 */
public class BiasNeuron extends AbstractNeuron {

	/**
	 * Instanziiert das Objekt und setzt dessen Wert auf 1.
	 */
	public BiasNeuron() {
		this.value = 1.0;
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
