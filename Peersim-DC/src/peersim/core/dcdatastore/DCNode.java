package peersim.core.dcdatastore;

import peersim.core.Node;
import peersim.transport.Transport;
import peersim.core.dcdatastore.util.Message;

public interface DCNode extends Node {
	
	public static final String PAR_TRANSPORT = "node.transport";
	
	public Transport getTransportLayer();
	
	public void sendMessage(Message<?> m, int pid);
}
