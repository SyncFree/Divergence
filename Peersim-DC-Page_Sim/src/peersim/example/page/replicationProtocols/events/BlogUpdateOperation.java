package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class BlogUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public BlogUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 100, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public BlogUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 100, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
