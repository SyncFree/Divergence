package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUnsubscribeOperation extends MoodleWriteOperation<Integer> {
	public ForumUnsubscribeOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 38, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ForumUnsubscribeOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 38, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
}
