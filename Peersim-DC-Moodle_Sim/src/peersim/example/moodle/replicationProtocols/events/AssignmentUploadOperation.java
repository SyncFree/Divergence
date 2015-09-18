package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class AssignmentUploadOperation extends MoodleWriteOperation<Integer> {
	public AssignmentUploadOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 4, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public AssignmentUploadOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 4, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
