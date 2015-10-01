package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;

public class MoodleReadOperation extends ClientReadOperation {

	private String userId;
	private String objId;

	public MoodleReadOperation(short operationID, ClientNode c, long time, String userId) {
		super(operationID, c, time);
		this.userId = userId;
		this.objId = "";
	}
	
	public MoodleReadOperation(short operationID, ClientNode c, long time, String userId, String objId) {
		super(operationID, c, time);
		this.userId = userId;
		this.objId = objId;
	}
	
	public MoodleReadOperation(short operationID, ClientNode c, ServerNode d, long time, String userId, String objId) {
		super(operationID, c, d, time);
		this.userId = userId;
		this.objId = objId;
	}

	public String getUserId(){		
		return this.userId;
	}
	
	public String getOperationId(){		
		return this.objId;
	}
	
}
