package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class PageViewOperation extends MoodleReadOperation {
	
	public PageViewOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 74, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public PageViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 74, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
}
