package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ResourceAddOperation extends MoodleWriteOperation<Integer> {

	public ResourceAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 56, c, d, time, userId);
		this.setObjectID(objId);
	}
	
	public ResourceAddOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 56, c, time, userId);
		this.setObjectID(objId);
	}
}
