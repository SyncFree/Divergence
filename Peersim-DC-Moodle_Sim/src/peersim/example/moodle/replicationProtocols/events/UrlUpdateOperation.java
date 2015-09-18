package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class UrlUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public UrlUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 62, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public UrlUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 62, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
