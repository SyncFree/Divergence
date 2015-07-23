package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class QuizUpdateOperation extends MoodleWriteOperation<Integer> {
	public QuizUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 49, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public QuizUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 49, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
