package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class UrlDeleteOperation extends MoodleWriteOperation<Integer> {
	
	public UrlDeleteOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 63, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public UrlDeleteOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 63, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
