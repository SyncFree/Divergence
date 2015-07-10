package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public abstract class ClientReadOperation extends ClientBaseOperation implements
		ClientOperation {

	public ClientReadOperation(short operationID) {
		super(operationID);
	}
	
	public ClientReadOperation(short operationID, ClientNode c) {
		super(operationID, c);
	}
	
	public ClientReadOperation(short operationID, ClientNode c, long time) {
		super(operationID, c, time);
	}
	
	public ClientReadOperation(short operationID, ClientNode c, ServerNode d, long time) {
		super(operationID,c,d,time);
	}

}
