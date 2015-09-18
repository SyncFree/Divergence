package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class AssignmentUpdateGradesOperation extends MoodleWriteOperation<Integer> {
	
	public AssignmentUpdateGradesOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 2, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public AssignmentUpdateGradesOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 2, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
