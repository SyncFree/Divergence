package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class BlogViewOperation extends MoodleReadOperation {
	
	public BlogViewOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 11, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public BlogViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 11, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
}
