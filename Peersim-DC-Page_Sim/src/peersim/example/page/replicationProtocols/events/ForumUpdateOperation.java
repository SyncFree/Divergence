package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public ForumUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 40, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ForumUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 40, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
