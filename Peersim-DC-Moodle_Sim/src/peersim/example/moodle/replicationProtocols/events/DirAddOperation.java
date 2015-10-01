package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class DirAddOperation extends MoodleWriteOperation<Integer> {
	public DirAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 29, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public DirAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 29, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
