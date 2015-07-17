package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUpdateDiscussionOperation extends MoodleWriteOperation<Integer> {
	
	public ForumUpdateDiscussionOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 39, c, time, userId);
		this.setObjectID(objId);
	}
	
	public ForumUpdateDiscussionOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 39, c, d, time, userId);
		this.setObjectID(objId);

	}
}
