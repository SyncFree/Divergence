package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class DirViewOperation extends MoodleReadOperation {
	
	public DirViewOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 32, c, time, userId);
		this.setObjectID(objId);
	}
	
	public DirViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 32, c, d, time, userId);
		this.setObjectID(objId);
	}
}
