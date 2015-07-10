package peersim.core.dcdatastore.controls;

import peersim.core.Node;

public class NextPeriodicSync {

	public final Node node;
	public final short protocolID;
	
	public NextPeriodicSync(Node node, short protocolID) {
		this.node = node;
		this.protocolID = protocolID;
	}

}
