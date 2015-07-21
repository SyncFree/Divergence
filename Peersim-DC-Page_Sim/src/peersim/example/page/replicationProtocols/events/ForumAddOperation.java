package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ForumAddOperation extends MoodleWriteOperation<Integer> {

	public ForumAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 34, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ForumAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 34, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
