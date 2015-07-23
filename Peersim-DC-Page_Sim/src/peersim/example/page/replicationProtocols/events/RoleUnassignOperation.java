package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class RoleUnassignOperation extends MoodleWriteOperation<Integer> {
	
	public RoleUnassignOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 26, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public RoleUnassignOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 26, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
