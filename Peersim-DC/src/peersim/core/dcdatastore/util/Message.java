package peersim.core.dcdatastore.util;

import peersim.core.GeneralNode;

public interface Message<T> {
	
	public GeneralNode getSender();
	
	public GeneralNode getDestination();
	
	public long getSendTime();
	
	public long getDeliveryTime();
	
	public void markSent();
	
	public void markReceived();
	
	public T getPayload();
}
