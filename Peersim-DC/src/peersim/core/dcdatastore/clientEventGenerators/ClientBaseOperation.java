package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public abstract class ClientBaseOperation implements ClientOperation {

	private ClientNode client;
	private ServerNode destination;
	private long timeOfCreation;
	private short operationID;
	
	private short clientProtocolID;
	private short serverProtocolID;
	
	private String objectID;
	
	public ClientBaseOperation(short operationID) {
		this.operationID = operationID;
		this.timeOfCreation = -1;
	}
	
	public ClientBaseOperation(short operationID, ClientNode c) {
		this.operationID = operationID;
		this.client = c;
		this.timeOfCreation = -1;
	}
	
	public ClientBaseOperation(short operationID, ClientNode c, long time) {
		this.operationID = operationID;
		this.client = c;
		this.timeOfCreation = time;
	}
	
	public ClientBaseOperation(short operationID, ClientNode c, ServerNode d, long time) {
		this.client = c;
		this.destination = d;
		this.timeOfCreation = time;
	}
	
	public ClientNode getClient() {
		return this.client;
	}
	
	public ServerNode getDestination() {
		return this.destination;
	}
	
	public long getTimeOfCreation() {
		return this.timeOfCreation;
	}
	
	public short operationID() {
		return this.operationID;
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

	public void setDestination(ServerNode server) {
		this.destination = server;
	}

	public void setTimeOfCreation(long time) {
		this.timeOfCreation = time;
	}

	public void setClient(ClientNode node) {
		this.client = node;
	}
	
	public void setObjectID(String key) {
		this.objectID = key;
	}
	
	public String getObjectID() {
		return this.objectID;
	}
}
