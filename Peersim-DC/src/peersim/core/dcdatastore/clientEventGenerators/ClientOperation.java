package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public interface ClientOperation {

	public ClientNode getClient();
	
	public ServerNode getDestination();
	
	public long getTimeOfCreation();
	
	public short operationID();
	
	public short getClientProtocolID();
	public short getServerProtocolID();
	public void setClientProtocolID(short id);
	public void setServerProtocolID(short id);

	public void setDestination(ServerNode server);

	public void setTimeOfCreation(long time);

	public void setClient(ClientNode node);
	
	public String getObjectID();
	public void setObjectID(String value);
	
}
