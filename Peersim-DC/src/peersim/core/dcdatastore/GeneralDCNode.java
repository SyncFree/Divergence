package peersim.core.dcdatastore;

import peersim.config.Configuration;
import peersim.core.GeneralNode;
import peersim.core.dcdatastore.datatypes.Message;
import peersim.transport.Transport;

public class GeneralDCNode extends GeneralNode implements DCNode, Cloneable {

	private static final String PAR_TRANSPORT = "node.transport";
	private static String transport;
	
	public GeneralDCNode(String prefix) {
		super(prefix);
		transport = Configuration.getString(PAR_TRANSPORT);
	}

	public GeneralDCNode clone() {
		return (GeneralDCNode) super.clone();
	}
	
	public Transport getTransportLayer() {
		return (Transport) this.getProtocol(Configuration.getPid(transport));
	}

	public void sendMessage(Message<?> m, int pid) {
		getTransportLayer().send(this, m.getDestination(), m, pid);
	}


}
