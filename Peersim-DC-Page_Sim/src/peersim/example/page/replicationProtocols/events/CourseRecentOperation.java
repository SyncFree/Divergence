package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class CourseRecentOperation extends MoodleReadOperation {
	
	public CourseRecentOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 666, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseRecentOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 666, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
}
