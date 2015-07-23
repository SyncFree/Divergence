package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class RoleAssignOperation extends MoodleWriteOperation<Integer> {
	
	public RoleAssignOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 25, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public RoleAssignOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 25, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
