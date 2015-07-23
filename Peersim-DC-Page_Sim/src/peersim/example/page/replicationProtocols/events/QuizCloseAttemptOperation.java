package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class QuizCloseAttemptOperation extends MoodleWriteOperation<Integer> {
	public QuizCloseAttemptOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 47, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public QuizCloseAttemptOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 47, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
