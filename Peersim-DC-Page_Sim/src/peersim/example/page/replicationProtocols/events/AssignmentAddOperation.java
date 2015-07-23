package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class AssignmentAddOperation extends MoodleWriteOperation<Integer> {
	
	public AssignmentAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 1, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public AssignmentAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 1, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
