package peersim.example.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class CounterIncrementOperation extends ClientWriteOperation<Integer> {

	public CounterIncrementOperation() {
		super((short) 2);
	}
	
	public CounterIncrementOperation(ClientNode c) {
		super((short) 2, c);
	}
	
	public CounterIncrementOperation(ClientNode c, long time) {
		super((short) 2, c, time);
	}
	
	public CounterIncrementOperation(ClientNode c, ServerNode d, long time) {
		super((short) 2, c, d, time);
	}
	



}
