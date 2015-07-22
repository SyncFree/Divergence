package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class UrlAddOperation extends MoodleWriteOperation<Integer> {
	public UrlAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 61, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public UrlAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 61, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
