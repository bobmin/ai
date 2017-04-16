package bob.nn;

/**
 * Ein Bias hat immer den Wert 1.
 * 
 * @author bobmin
 *
 */
public class BiasNeuron extends Neuron {

	/**
	 * Instanziiert das Objekt und setzt dessen Wert auf 1.
	 */
	public BiasNeuron() {
		this.value = 1.0;
	}

}
