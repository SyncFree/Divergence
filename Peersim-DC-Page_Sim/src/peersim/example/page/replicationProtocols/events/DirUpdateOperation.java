package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class DirUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public DirUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 31, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public DirUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 31, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
