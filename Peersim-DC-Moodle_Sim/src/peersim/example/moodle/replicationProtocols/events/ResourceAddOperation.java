package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ResourceAddOperation extends MoodleWriteOperation<Integer> {

	public ResourceAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId ,String courseId) {
		super((short) 56, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ResourceAddOperation(ClientNode c, long time, String userId, String objId ,String courseId) {
		super((short) 56, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
