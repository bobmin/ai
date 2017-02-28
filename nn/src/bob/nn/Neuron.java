package bob.nn;

abstract public class Neuron {

	private static int counter = 0;

	private final int id;

	private String name = "NO NAME";

	public Neuron() {
		id = counter;
		counter++;
	}

	public int getId() {
		return id;
	}

	abstract public double getOutput();

	@Override
	public String toString() {
		return String.format("%s [id = %d]", this.getClass().getName(), id);
	}

}
