package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseEnrolOperation extends MoodleWriteOperation<Integer> {
	
	public CourseEnrolOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 19, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseEnrolOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 19, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
