package peersim.core.dcdatastore.clientEventGenerators;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public abstract class ClientWriteOperation<T> extends ClientBaseOperation implements ClientOperation {

	private T value;

	public ClientWriteOperation(short operationID) {
		super(operationID);
	}
	
	public ClientWriteOperation(short operationID, ClientNode c) {
		super(operationID, c);
	}
	
	public ClientWriteOperation(short operationID, ClientNode c, long time) {
		super(operationID, c, time);
	}
	
	public ClientWriteOperation(short operationID, ClientNode c, ServerNode d, long time) {
		super(operationID, c, d, time);
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

}
