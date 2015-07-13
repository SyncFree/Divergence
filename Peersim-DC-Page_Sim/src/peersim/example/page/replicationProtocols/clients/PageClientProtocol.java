package peersim.example.page.replicationProtocols.clients;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.clientEventGenerators.Reply;
import peersim.core.dcdatastore.clientSupport.ClientLayer;

public class PageClientProtocol extends ClientLayer {

	public PageClientProtocol(String basename) {
		super(basename);
	}

	@Override
	protected void processServerReply(ClientNode node, int pid, Reply<?> event) {
		//Ignore the reply.
	}

}
