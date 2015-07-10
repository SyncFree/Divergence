package peersim.core.dcdatastore.serverEvents;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class SimpleOperationPropagationEvent implements OperationPropagationEvent {

	private ServerNode originatorServer;
	private ClientWriteOperation<?> op;
	
	public SimpleOperationPropagationEvent(ServerNode node) {
		this.originatorServer = node;
		this.op = null;
	}
	
	public SimpleOperationPropagationEvent(ServerNode node, ClientWriteOperation<?> op) {
		this.originatorServer = node;
		this.op = op;
	}

	public ServerNode getOriginatorServer() {
		return originatorServer;
	}

	public ClientWriteOperation<?> getClientOperation() {
		return op;
	}

	public void addOperation(ClientWriteOperation<?> op) {
		this.op = op;
	}

}
