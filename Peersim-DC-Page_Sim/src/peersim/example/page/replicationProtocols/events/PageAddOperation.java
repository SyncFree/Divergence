package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class PageAddOperation extends MoodleWriteOperation<Integer> {

	public PageAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 73, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public PageAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 73, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
