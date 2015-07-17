package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;

public class MoodleReadOperation extends ClientReadOperation {

	private String userId;
	
	public MoodleReadOperation(short operationID, ClientNode c, long time, String userId) {
		super(operationID, c, time);
		this.userId = userId;
	}
	
	public MoodleReadOperation(short operationID, ClientNode c, ServerNode d, long time, String userId) {
		super(operationID, c, d, time);
		this.userId = userId;
	}

	public String getUserId(){		
		return this.userId;
	}
	
}
