package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumViewAllForumsOperation extends MoodleReadOperation {

	public ForumViewAllForumsOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 37, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ForumViewAllForumsOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 37, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
}
