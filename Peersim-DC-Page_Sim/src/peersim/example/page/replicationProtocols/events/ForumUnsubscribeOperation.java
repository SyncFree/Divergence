package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUnsubscribeOperation extends MoodleWriteOperation<Integer> {
	public ForumUnsubscribeOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 38, c, time, userId);
		this.setObjectID(objId);
	}
	
	public ForumUnsubscribeOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 38, c, d, time, userId);
		this.setObjectID(objId);
	}
	
}
