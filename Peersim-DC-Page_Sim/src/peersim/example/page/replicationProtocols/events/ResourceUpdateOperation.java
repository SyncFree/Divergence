package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ResourceUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public ResourceUpdateOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 58, c, time, userId);
		this.setObjectID(objId);
	}
	
	public ResourceUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 58, c, d, time, userId);
		this.setObjectID(objId);
	}
	

}
