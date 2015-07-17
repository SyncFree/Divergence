package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ResourceViewOperation extends MoodleReadOperation {
	
	public ResourceViewOperation(ClientNode c, long time, String pageId, String userId) {
		super((short) 60, c, time, userId);
		this.setObjectID(pageId);
	}
	
	public ResourceViewOperation(ClientNode c, ServerNode d, long time, String pageId, String userId) {
		super((short) 60, c, d, time, userId);
		this.setObjectID(pageId);
	}
}
