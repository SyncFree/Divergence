package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public class PageUpdateOperation extends MoodleOperation<Integer> {
	
	public PageUpdateOperation(ClientNode c, long time, String pageId, String userId) {
		super((short) 75, c, time, userId);
		this.setObjectID(pageId);
	}
	
	public PageUpdateOperation(ClientNode c, ServerNode d, long time, String pageId, String userId) {
		super((short) 75, c, d, time, userId);
		this.setObjectID(pageId);
	}
	

}
