package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.clientEventGenerators.BaseReadReply;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;

import peersim.example.page.replicationsProtocols.data.PageSim;

public class PageSimReadReply extends BaseReadReply<PageSim> {

	public PageSimReadReply(ClientReadOperation req, PageSim reply,
			long timestamp) {
		super(req, reply, timestamp);
	}

}
