package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public CourseUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 16, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 16, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
