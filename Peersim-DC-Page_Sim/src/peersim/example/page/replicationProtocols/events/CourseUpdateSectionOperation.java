package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseUpdateSectionOperation extends MoodleWriteOperation<Integer> {
	
	public CourseUpdateSectionOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 17, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseUpdateSectionOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 17, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
