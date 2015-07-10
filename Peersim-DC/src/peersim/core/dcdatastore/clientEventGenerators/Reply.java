package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public interface Reply<T> {

	public ServerNode getServer();
	
	public ClientNode getDestination();
	
	public long getTimeOfCreation();
	
	public short operationID();
	
	public short getClientProtocolID();
	public short getServerProtocolID();
	public void setClientProtocolID(short id);
	public void setServerProtocolID(short id);
	
	public String getObjectID();
	
	public T getPayload();
	
}
