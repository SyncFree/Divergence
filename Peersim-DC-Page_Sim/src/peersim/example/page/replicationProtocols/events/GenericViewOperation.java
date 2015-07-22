package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class GenericViewOperation extends MoodleReadOperation {
	
	public GenericViewOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 666, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public GenericViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 666, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
}
