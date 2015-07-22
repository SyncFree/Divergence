package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseEnrolGuestOperation extends MoodleWriteOperation<Integer> {
	
	public CourseEnrolGuestOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 18, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseEnrolGuestOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 18, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
