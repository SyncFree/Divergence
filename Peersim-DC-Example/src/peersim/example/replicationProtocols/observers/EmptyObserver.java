package peersim.example.replicationProtocols.observers;

import peersim.core.Control;

public class EmptyObserver implements Control {

	public EmptyObserver(String basename) {
		
	}

	public boolean execute() {
		//System.err.println(DCCommonState.getTime() + ": Triggering " + this.getClass().getCanonicalName());
		return false;
	}

}
