package bob.nn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class NetworkBuilder {

	private InputLayer inputLayer = null;

	private Deque<WorkingLayer> hiddenLayers = null;

	private WorkingLayer outputLayer = null;

	public NetworkBuilder() {
	}

	public void initInputLayer(final int count, final boolean useBias) {
		if (null != inputLayer) {
			throw new IllegalStateException("[inputLayer] is already exists");
		}
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		inputLayer = new InputLayer(count, useBias);
	}

	public void initHiddenLayers(final int count) {
		if (null != hiddenLayers) {
			throw new IllegalStateException("[hiddenLayers] is already exists");
		}
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		hiddenLayers = new ArrayDeque<WorkingLayer>(count);
	}

	public void initHiddenLayer(final int count, final boolean useBias) {
		if (null == hiddenLayers) {
			throw new IllegalStateException("[hiddenLayers] is not yet initialized");
		}
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		final WorkingLayer x = new WorkingLayer(count, useBias);
		if (0 == hiddenLayers.size()) {
			connect(inputLayer, x);
		} else {
			connect(hiddenLayers.getLast(), x);
		}
		hiddenLayers.addLast(x);
	}

	public void initOutputLayer(final int count) {
		if (null != outputLayer) {
			throw new IllegalStateException("[outputLayer] is already exists");
		}
		if (1 > count) {
			throw new IllegalArgumentException("[count] cannot be less than 1");
		}
		outputLayer = new WorkingLayer(count, false);
		if (null == hiddenLayers || 0 == hiddenLayers.size()) {
			connect(inputLayer, outputLayer);
		} else {
			connect(hiddenLayers.getLast(), outputLayer);
		}
	}

	/**
	 * Verbindet jedes Neuronen von <code>left</code> mit jedem Neuron von
	 * <code>right</code>.
	 * 
	 * @param left
	 *            die Neuronen-Quelle
	 * @param right
	 *            das Neuronen-Ziel
	 */
	private void connect(final AbstractLayer<? extends AbstractNeuron> leftLayer, final WorkingLayer rightLayer) {
		final List<? extends AbstractNeuron> leftNeurons = leftLayer.getNeurons();
		final List<WorkingNeuron> rightNeurons = rightLayer.getNeurons();
		for (WorkingNeuron r : rightNeurons) {
			if (leftLayer.hasBias()) {
				final BiasNeuron b = leftLayer.getBias();
				final Connection connection = new Connection(b, r);
				b.registerOutgoing(connection);
				r.registerIncoming(connection);
			}
			for (AbstractNeuron l : leftNeurons) {
				final Connection connection = new Connection(l, r);
				l.registerOutgoing(connection);
				r.registerIncoming(connection);
			}
		}
	}

	public Network createNetwork() {
		if (null == inputLayer) {
			throw new IllegalStateException("[inputLayer] is not yet initialized");
		}
		if (null == outputLayer) {
			throw new IllegalStateException("[outputLayer] is not yet initialized");
		}
		return new Network(inputLayer, hiddenLayers, outputLayer);
	}

}
