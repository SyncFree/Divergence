package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class CourseAddModuleOperation extends MoodleWriteOperation<Integer> {
	public CourseAddModuleOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 20, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseAddModuleOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 20, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
