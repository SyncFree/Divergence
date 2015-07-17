package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class PageViewOperation extends MoodleReadOperation {
	
	public PageViewOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 74, c, time, userId);
		this.setObjectID(objId);
	}
	
	public PageViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 74, c, d, time, userId);
		this.setObjectID(objId);
	}
}
