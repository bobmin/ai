package bob.demo;

import java.util.List;
import java.util.Set;

import bob.nn.BackpropagationTrainer;
import bob.nn.Connection;
import bob.nn.WorkingNeuron;

public class BackpropagationDemo extends AbstractDemo {

	/** die Trainingsdaten */
	// @formatter:off
	private static final double[][][] TRAIN = { 
			{ { 0.7, 0.6 }, { 0.9, 0.2 } } 
	};
	// formatter:on
	
	/** das Training */
	private final BackpropagationTrainer trainer;

	public static void main(String[] args) {
		new BackpropagationDemo();
	}

	public BackpropagationDemo() {
		super(2, true, new int[] { 2 }, new boolean[] { true }, 2);

		final List<WorkingNeuron> hiddenNeurons = network.getHiddenLayers().get(0).getNeurons();
		setWeights(hiddenNeurons.get(0), 0.3, 0.8, 0.5);
		setWeights(hiddenNeurons.get(1), -0.2, -0.6, 0.7);

		final List<WorkingNeuron> outputNeurons = network.getOutputLayer().getNeurons();
		setWeights(outputNeurons.get(0), 0.2, 0.4, 0.3);
		setWeights(outputNeurons.get(1), 0.1, -0.4, 0.9);

		printer.text("Zwei Eingabe- und zwei Ausgabeneuronen lernen über zwei versteckten Neuronen");
		printer.text("ein definierte Ergebnis. In der Eingabeschicht und der versteckte Schicht");
		printer.text("existiert zusätzlich ein BIAS.");
		printer.text("Zuerst startet die Lernphase. Im Anschluss kann das Netz befragt werden.");
		printer.separator();

		printer.print(network);
		
		trainer = new BackpropagationTrainer(network, this);
		trainer.doIt();

		printer.separator();
		printer.text("BYE!");
	}

	private void setWeights(final WorkingNeuron n, double... values) {
		final Set<Connection> connections = n.getInputs();
		int idx = 0;
		for (Connection c : connections) {
			c.setWeight(values[idx]);
			idx++;
		}
	}

	// --- Training ----------------------------------------------------------

	@Override
	public double[][][] getData() {
		return TRAIN;
	}

	@Override
	public void startLoop(int loop) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showData(int loop, int index, double actual, double localError) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishLoop(int loop, double globalError) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getAutoLines(double globalError) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getGlobalErrorStop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stopTrainer() {
		// TODO Auto-generated method stub

	}

}
