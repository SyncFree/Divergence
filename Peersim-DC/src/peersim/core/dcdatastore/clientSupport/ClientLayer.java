package peersim.core.dcdatastore.clientSupport;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Node;
import peersim.core.Protocol;
import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.clientEventGenerators.BaseClientOperationGenerator;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperation;
import peersim.core.dcdatastore.clientEventGenerators.Reply;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public abstract class ClientLayer implements Protocol, EDProtocol, Cloneable {

	protected static final String PAR_SERVER_PROTO = "remoteproto";
	protected static final String PAR_TRASPORT_PROTO = "transport";
	
	protected static int serverProtocolID;
	protected static int transport;
	
	public ClientLayer(String basename) {
		ClientLayer.serverProtocolID = Configuration.lookupPid(Configuration.getString(basename + "." + PAR_SERVER_PROTO));
		ClientLayer.transport = Configuration.lookupPid(Configuration.getString(basename + "." + PAR_TRASPORT_PROTO));
		//BaseClientOperationGenerator.clientProtocolID = (short) Configuration.lookupPid(basename.replace("client.protocol.", ""));
	}
	
	public ClientLayer clone() {
		ClientLayer cl = null;
		try{
			cl = (ClientLayer) super.clone();
		} catch (CloneNotSupportedException e) {
			//Never happens
		}
		return cl;
	}

	public void processEvent(Node node, int pid, Object event) {
		if(event instanceof ClientOperation) {
			//this is an operation to be issued by this client.
			this.issueClientOperation((ClientNode) node, pid, (ClientOperation) event);
		} else if (event instanceof Reply) {
			this.processServerReply((ClientNode) node, pid, (Reply<?>) event);
		}
	}

	protected abstract void processServerReply(ClientNode node, int pid, Reply<?> event);

	private final void issueClientOperation(ClientNode node, int pid,
			ClientOperation event) {
		event.setClientProtocolID((short) pid);
		event.setServerProtocolID((short) ClientLayer.serverProtocolID);
		event.setDestination(node.getServer(CommonState.r.nextInt(node.getNumberServer())));
		event.setTimeOfCreation(CommonState.getTime());
		event.setClient(node);
		((Transport)node.getProtocol(ClientLayer.transport)).send(node, event.getDestination(), event, pid);   
	}

}
