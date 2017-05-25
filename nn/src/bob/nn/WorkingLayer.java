package bob.nn;

public class WorkingLayer extends AbstractLayer<WorkingNeuron> {

	public WorkingLayer(final int count, final boolean useBias) {
		super(count, WorkingNeuron.class, useBias);
	}

}
