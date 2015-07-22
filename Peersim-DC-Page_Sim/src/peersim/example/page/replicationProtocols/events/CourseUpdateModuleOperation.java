package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CourseUpdateModuleOperation extends MoodleWriteOperation<Integer> {
	
	public CourseUpdateModuleOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 22, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CourseUpdateModuleOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 22, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
