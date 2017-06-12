package bob.demo;

import bob.nn.BackpropagationTrainer;
import bob.nn.activation.SigmoidActivation;

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

		printer.text("Zwei Eingabe- und zwei Ausgabeneuronen lernen über zwei versteckten Neuronen");
		printer.text("ein definierte Ergebnis. In der Eingabeschicht und der versteckte Schicht");
		printer.text("existiert zusätzlich ein BIAS.");
		printer.text("Zuerst startet die Lernphase. Im Anschluss kann das Netz befragt werden.");
		printer.separator();

		network.getHiddenLayer(0).getNeuron(0).setActivation(new SigmoidActivation());
		network.getHiddenLayer(0).getNeuron(0).setIncomingWeights(0.3,  0.8, 0.5);
		network.getHiddenLayer(0).getNeuron(1).setActivation(new SigmoidActivation());
		network.getHiddenLayer(0).getNeuron(1).setIncomingWeights(-0.2, -0.6, 0.7);

		network.getOutputLayer().getNeuron(0).setActivation(new SigmoidActivation());
		network.getOutputLayer().getNeuron(0).setIncomingWeights(0.2, 0.4, 0.3);
		network.getOutputLayer().getNeuron(1).setActivation(new SigmoidActivation());
		network.getOutputLayer().getNeuron(1).setIncomingWeights(0.1, -0.4, 0.9);

		// Netzwerk berechnen
		
		network.forward(0.7, 0.6);
		printer.print(network);
		
		// Gwichte justieren
		
		printer.separator();
		printer.showError(network, 0.2, 0.9);

		printer.separator();
		network.backward(0.2, 0.9);
		printer.print(network);
		
		
		trainer = new BackpropagationTrainer(network, this);
		trainer.doIt();

		printer.separator();
		printer.text("BYE!");
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
