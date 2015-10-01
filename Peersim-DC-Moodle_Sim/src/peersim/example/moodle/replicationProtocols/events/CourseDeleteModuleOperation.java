package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseDeleteModuleOperation extends MoodleWriteOperation<Integer> {
	
	public CourseDeleteModuleOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 21, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseDeleteModuleOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 21, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
