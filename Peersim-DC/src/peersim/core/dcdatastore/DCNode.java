package peersim.core.dcdatastore;

import peersim.core.Node;
import peersim.core.dcdatastore.datatypes.Message;
import peersim.transport.Transport;

public interface DCNode extends Node {
	
	public static final String PAR_TRANSPORT = "node.transport";
	
	public Transport getTransportLayer();
	
	public void sendMessage(Message<?> m, int pid);
}
