package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public ForumUpdateOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 40, c, time, userId);
		this.setObjectID(objId);
	}
	
	public ForumUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 40, c, d, time, userId);
		this.setObjectID(objId);
	}
	

}
