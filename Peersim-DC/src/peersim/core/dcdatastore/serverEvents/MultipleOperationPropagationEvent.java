package peersim.core.dcdatastore.serverEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class MultipleOperationPropagationEvent implements
		OperationPropagationEvent {

	private ServerNode originatorServer;
	private Collection<ClientWriteOperation<?>> ops;
	
	public MultipleOperationPropagationEvent(ServerNode node) {
		this.originatorServer = node;
		this.ops = new ArrayList<ClientWriteOperation<?>>();
	}
	
	public MultipleOperationPropagationEvent(ServerNode node, Collection<ClientWriteOperation<?>> ops) {
		this.originatorServer = node;
		this.ops = new ArrayList<ClientWriteOperation<?>>(ops);
	}

	public ServerNode getOriginatorServer() {
		return this.originatorServer;
	}

	public Iterator<ClientWriteOperation<?>> getClientOperations() {
		return ops.iterator();
	}

	public void addOperation(ClientWriteOperation<?> op) {
		this.ops.add(op);
	}

}
