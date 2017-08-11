package bob.demo;

import bob.nn.Network;
import bob.nn.NetworkBuilder;
import bob.nn.Printer;
import bob.nn.activation.SigmoidActivation;

public class BackpropagationDemo {

	// @formatter:off
	/** 
	 * die Trainingsdaten
	 * -- {
	 * -- -- { Eingabe }, 
	 * -- -- { Ausgabe }
	 * -- } 
	 */
	private static final double[][][] TRAIN = { 
			{ 
			  { 0.7, 0.6 }, 
			  { 0.9, 0.2 } 
			} 
	};
	// formatter:on
	
	/** die formatierte Konsolenausgabe des Netzwerks */
	private final Printer printer;
	
	public static void main(String[] args) {
		new BackpropagationDemo();
	}

	public BackpropagationDemo() {
		printer = new Printer(System.out);

		printer.text("Zwei Eingabe- und zwei Ausgabeneuronen lernen über zwei versteckten Neuronen");
		printer.text("ein definierte Ergebnis. In der Eingabeschicht und der versteckte Schicht");
		printer.text("existiert zusätzlich ein BIAS.");
		printer.text("Zuerst startet die Lernphase. Im Anschluss kann das Netz befragt werden.");
		printer.separator();
		
		final Network network = initNetwork();

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
		
		
		printer.separator();
		printer.text("BYE!");
	}

	/**
	 * Erstellt das Netzwerk zur Demo.
	 * @return ein Objekt, niemals <code>null</code>
	 */
	private Network initNetwork() {
		final NetworkBuilder builder = new NetworkBuilder();
		builder.initInputLayer(2, true);
		builder.initHiddenLayers(1);
		builder.initHiddenLayer(2, true);
		builder.initOutputLayer(2);
		return builder.createNetwork();
	}
	
}
