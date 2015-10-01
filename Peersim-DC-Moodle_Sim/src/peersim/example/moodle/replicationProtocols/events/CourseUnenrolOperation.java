package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class CourseUnenrolOperation extends MoodleWriteOperation<Integer> {
	public CourseUnenrolOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 27, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseUnenrolOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 27, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
