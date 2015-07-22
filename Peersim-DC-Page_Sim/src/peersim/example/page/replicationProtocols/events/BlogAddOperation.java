package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class BlogAddOperation extends MoodleWriteOperation<Integer> {
	public BlogAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 9, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public BlogAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 9, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
