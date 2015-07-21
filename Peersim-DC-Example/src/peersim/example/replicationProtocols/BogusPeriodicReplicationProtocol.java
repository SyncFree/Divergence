package peersim.example.replicationProtocols;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import peersim.core.CommonState;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.replicationProtocol.PeriodicReplicationProtocol;
import peersim.core.dcdatastore.serverEvents.MultipleOperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.example.replicationProtocols.events.CounterIncrementOperation;
import peersim.example.replicationProtocols.events.CounterReadReply;
import peersim.example.replicationsProtocols.data.Counter;

public class BogusPeriodicReplicationProtocol extends
		PeriodicReplicationProtocol implements Cloneable {
	
	private List<CounterIncrementOperation> operations;
	
	public BogusPeriodicReplicationProtocol(String name) {
		super(name);
		this.operations = new ArrayList<CounterIncrementOperation>();
	}
	
	public BogusPeriodicReplicationProtocol clone() {
		BogusPeriodicReplicationProtocol bprp = null;
		bprp = (BogusPeriodicReplicationProtocol) super.clone();
		bprp.operations = new ArrayList<CounterIncrementOperation>();
		return bprp;
	}

	@Override
	public boolean handleClientWriteRequest(ServerNode node, int pid,
			ClientWriteOperation<?> event) {
		
		//System.err.println("@"+DCCommonState.getTime() + " => " + this.getClass().getCanonicalName() + ": processing " + event.getClass().getName());
		
		CounterIncrementOperation operation = (CounterIncrementOperation) event;
		Counter c = (Counter) node.read(operation.getObjectID());
		if(c == null) { 
			c = new Counter();
			node.write(operation.getObjectID(), c);
		}
		c.increment(operation.getValue());
		operations.add(operation);
		return true;
	}

	@Override
	public void handleClientReadRequest(ServerNode node, int pid,
			ClientReadOperation event) {
		Counter c = (Counter) node.read(event.getObjectID());
		CounterReadReply crr = new CounterReadReply(event, c, CommonState.getTime());
		this.replyToClient(node, crr.getClientProtocolID(), crr);
	}

	@Override
	public void handleServerPropagationRequest(ServerNode node, int pid,
			OperationPropagationEvent event) {
		//System.err.println("@" + DCCommonState.getTime() + " " + this.getClass().getCanonicalName() + ": delivering event of type: " + event.getClass().getName());
		Iterator<ClientWriteOperation<?>> ite = ((MultipleOperationPropagationEvent) event).getClientOperations();
		while(ite.hasNext()) {
			CounterIncrementOperation operation = (CounterIncrementOperation) ite.next();
			Counter c = (Counter) node.read(operation.getObjectID());
			if(c == null) { 
				c = new Counter();
				node.write(operation.getObjectID(), c);
			}
			c.increment(operation.getValue());
		}
	}

	@Override
	public OperationPropagationEvent getStateToPropagate(ServerNode node) {
		OperationPropagationEvent ope = new MultipleOperationPropagationEvent(node);
		for(ClientWriteOperation<?> op: this.operations)
			ope.addOperation(op);
		return ope;
	}

}
