package peersim.example.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;

public class CounterReadOperation extends ClientReadOperation {

	public CounterReadOperation() {
		super((short) 1);
	}

	public CounterReadOperation(ClientNode c) {
		super((short) 1, c);
	}

	public CounterReadOperation(ClientNode c, long time) {
		super((short) 1, c, time);
	}

	public CounterReadOperation(ClientNode c, ServerNode d,
			long time) {
		super((short) 1, c, d, time);
	}

}
