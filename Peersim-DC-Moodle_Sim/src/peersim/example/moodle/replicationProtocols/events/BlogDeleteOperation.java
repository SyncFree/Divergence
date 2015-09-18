package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class BlogDeleteOperation extends MoodleWriteOperation<Integer> {
	
	public BlogDeleteOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 10, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public BlogDeleteOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 10, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
