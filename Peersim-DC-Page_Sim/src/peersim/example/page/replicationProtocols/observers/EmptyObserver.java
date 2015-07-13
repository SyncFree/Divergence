package peersim.example.page.replicationProtocols.observers;

import peersim.core.Control;
import peersim.core.dcdatastore.DCCommonState;

public class EmptyObserver implements Control {

	public EmptyObserver(String basename) {
		
	}

	public boolean execute() {
		//System.err.println(DCCommonState.getTime() + ": Triggering " + this.getClass().getCanonicalName());
		return false;
	}

}
