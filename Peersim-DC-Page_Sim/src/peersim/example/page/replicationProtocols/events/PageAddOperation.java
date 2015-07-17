package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class PageAddOperation extends MoodleWriteOperation<Integer> {

	public PageAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 73, c, d, time, userId);
		this.setObjectID(objId);
	}
	
	public PageAddOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 73, c, time, userId);
		this.setObjectID(objId);
	}
}
