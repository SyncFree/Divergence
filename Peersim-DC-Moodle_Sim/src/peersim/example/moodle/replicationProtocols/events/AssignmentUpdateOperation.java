package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class AssignmentUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public AssignmentUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 3, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public AssignmentUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 3, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
