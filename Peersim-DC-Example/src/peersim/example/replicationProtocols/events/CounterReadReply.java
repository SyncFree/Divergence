package peersim.example.replicationProtocols.events;

import peersim.core.dcdatastore.clientEventGenerators.BaseReadReply;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.example.replicationsProtocols.data.Counter;

public class CounterReadReply extends BaseReadReply<Counter> {

	public CounterReadReply(ClientReadOperation req, Counter reply,
			long timestamp) {
		super(req, reply, timestamp);
	}

}
