package bob.nn;

import java.util.Objects;

/**
 * Ein gewichtete Verbindung zu einem Neuron.
 * 
 * @author bobmin
 *
 */
public class Connection {

	/** das Quellneuron */
	private final AbstractNeuron leftNeuron;

	/** das Zielneuron */
	private final WorkingNeuron rightNeuron;

	/** die Gewichtung */
	private double weight = Math.random();

	private double value = Double.NaN;

	private double output = Double.NaN;

	/**
	 * Instanziiert das Objekt mit einem zufälligem Gewicht.
	 * 
	 * @param leftNeuron
	 *            das Quellneuron
	 * @param rightNeuron
	 *            das Zielneuron
	 */
	public Connection(final AbstractNeuron leftNeuron, final WorkingNeuron rightNeuron) {
		Objects.requireNonNull(leftNeuron);
		Objects.requireNonNull(rightNeuron);
		this.leftNeuron = leftNeuron;
		this.rightNeuron = rightNeuron;
	}

	/**
	 * Liefert das Quellneuron.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	public AbstractNeuron getLeftNeuron() {
		return leftNeuron;
	}

	/**
	 * Liefert das Zielneuron.
	 * 
	 * @return ein Objekt, niemals <code>null</code>
	 */
	public WorkingNeuron getRightNeuron() {
		return rightNeuron;
	}

	/**
	 * Setz die Gewichtung.
	 * 
	 * @param value
	 *            die Gewichtung
	 */
	public void setWeight(final double value) {
		this.weight = value;
	}

	public void adjustWeight(double d) {
		weight = (leftNeuron.getOutput() * d);
	}

	/**
	 * Liefert die aktuelle Gewichtung.
	 * 
	 * @return eine Zahl
	 */
	public double getWeight() {
		return weight;
	}

	public double computeOutput() {
		value = leftNeuron.getOutput();
		output = value * weight;
		return output;
	}

	@Override
	public String toString() {
		return String.format("%s [neuron = %d, value = %.3f, weight = %.3f, output = %.3f]", this.getClass().getName(),
				leftNeuron.getId(), value, weight, output);
	}

}
