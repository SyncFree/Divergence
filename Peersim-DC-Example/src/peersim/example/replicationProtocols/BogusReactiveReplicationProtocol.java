package peersim.example.replicationProtocols;

import peersim.core.CommonState;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.replicationProtocol.ReactiveReplicationProtocol;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.SimpleOperationPropagationEvent;
import peersim.example.replicationProtocols.events.CounterIncrementOperation;
import peersim.example.replicationProtocols.events.CounterReadReply;
import peersim.example.replicationsProtocols.data.Counter;

public class BogusReactiveReplicationProtocol extends
		ReactiveReplicationProtocol implements Cloneable {

	public BogusReactiveReplicationProtocol(String name) {
		super(name);
		//Nothing to be done here.
	}

	public BogusReactiveReplicationProtocol clone() {
		BogusReactiveReplicationProtocol bprp = null;
		bprp = (BogusReactiveReplicationProtocol) super.clone();
		return bprp;
	}
	
	@Override
	public boolean handleClientWriteRequest(ServerNode node, int pid,
			ClientWriteOperation<?> event) {
		Counter c = (Counter) node.read(event.getObjectID());
		if(c == null) {
			c = new Counter();
			node.write(event.getObjectID(), c);
		}
		c.increment(((CounterIncrementOperation) event).getValue());
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
		CounterIncrementOperation cio = (CounterIncrementOperation) ((SimpleOperationPropagationEvent) event).getClientOperation();
		Counter c = (Counter) node.read(cio.getObjectID());
		if(c == null) {
			c = new Counter();
			node.write(cio.getObjectID(), c);
		}
		c.increment(cio.getValue());
	}

}
