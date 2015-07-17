package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class DirAddOperation extends MoodleWriteOperation<Integer> {

	public DirAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 29, c, d, time, userId);
		this.setObjectID(objId);
	}
	
	public DirAddOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 29, c, time, userId);
		this.setObjectID(objId);
	}
}
