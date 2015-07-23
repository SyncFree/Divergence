package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class QuizAttemptOperation extends MoodleWriteOperation<Integer> {
	public QuizAttemptOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 46, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public QuizAttemptOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 46, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
