package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class PageAddOperation extends MoodleWriteOperation<Integer> {

	public PageAddOperation(ClientNode c, ServerNode d, long time, String pageId, String userId) {
		super((short) 73, c, d, time, userId);
		this.setObjectID(pageId);
	}
	
	public PageAddOperation(ClientNode c, long time, String pageId, String userId) {
		super((short) 73, c, time, userId);
		this.setObjectID(pageId);
	}
}
