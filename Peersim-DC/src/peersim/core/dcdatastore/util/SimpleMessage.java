package peersim.core.dcdatastore.util;

import peersim.core.CommonState;
import peersim.core.GeneralNode;

public class SimpleMessage<T> implements Message<T> {

	private long sendTime;
	private long receiveTime;
	private GeneralNode sender;
	private GeneralNode receiver;
	private T payload;
	
	public SimpleMessage(GeneralNode sender, GeneralNode receiver, T payload) {
		this.sender = sender;
		this.receiver = receiver;
		this.payload = payload;
		this.sendTime = CommonState.getTime();
		this.receiveTime = -1;
	}

	public GeneralNode getSender() {
		return this.sender;
	}

	public GeneralNode getDestination() {
		return this.receiver;
	}

	public long getSendTime() {
		return this.sendTime;
	}

	public long getDeliveryTime() {
		return this.receiveTime;
	}

	public void markReceived() {
		this.receiveTime = CommonState.getTime();
	}

	public T getPayload() {
		return this.payload;
	}

	public void markSent() {
		this.sendTime = CommonState.getTime();
	}

}
