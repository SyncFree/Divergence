package peersim.core.dcdatastore.serverEvents;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public interface OperationPropagationEvent {

	public ServerNode getOriginatorServer();
	
	public void addOperation(ClientWriteOperation<?> op);

}
