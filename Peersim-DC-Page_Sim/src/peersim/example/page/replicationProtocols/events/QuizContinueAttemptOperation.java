package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class QuizContinueAttemptOperation extends MoodleWriteOperation<Integer> {
	public QuizContinueAttemptOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 48, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public QuizContinueAttemptOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 48, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
