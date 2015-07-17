package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ResourceViewOperation extends MoodleReadOperation {
	
	public ResourceViewOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 60, c, time, userId);
		this.setObjectID(objId);
	}
	
	public ResourceViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 60, c, d, time, userId);
		this.setObjectID(objId);
	}
}
