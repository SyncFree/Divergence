package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ResourceAddOperation extends MoodleWriteOperation<Integer> {

	public ResourceAddOperation(ClientNode c, ServerNode d, long time, String pageId, String userId) {
		super((short) 56, c, d, time, userId);
		this.setObjectID(pageId);
	}
	
	public ResourceAddOperation(ClientNode c, long time, String pageId, String userId) {
		super((short) 56, c, time, userId);
		this.setObjectID(pageId);
	}
}
