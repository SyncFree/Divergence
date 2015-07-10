package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public abstract class BaseReply<T> implements ReadReply<T> {

	private ServerNode server;
	private ClientNode destination;
	private long timeOfCreation;
	private short operationID;
	
	private short clientProtocolID;
	private short serverProtocolID;
	
	private String objectID;
	
	private T payload;
	
	public BaseReply(ClientReadOperation req, T reply, long timestamp) {
		this.server = req.getDestination();
		this.destination = req.getClient();
		this.timeOfCreation = timestamp;
		this.operationID = req.operationID();
		this.objectID = req.getObjectID();
		
		this.clientProtocolID = req.getClientProtocolID();
		this.serverProtocolID = req.getServerProtocolID();
		
		this.payload = reply;
	}

	public ServerNode getServer() {
		return this.server;
	}

	public ClientNode getDestination() {
		return destination;
	}

	public long getTimeOfCreation() {
		return timeOfCreation;
	}

	public short operationID() {
		return operationID;
	}

	public short getClientProtocolID() {
		return this.clientProtocolID;
	}
	
	public short getServerProtocolID() {
		return this.serverProtocolID;
	}
	
	public void setClientProtocolID(short id) {
		this.clientProtocolID = id;
	}

	public void setServerProtocolID(short id) {
		this.serverProtocolID = id;
	}
	
	public T getPayload() {
		return this.payload;
	}
	
	public String getObjectID() {
		return this.objectID;
	}

}
