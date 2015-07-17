package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public class PageUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public PageUpdateOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 75, c, time, userId);
		this.setObjectID(objId);
	}
	
	public PageUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 75, c, d, time, userId);
		this.setObjectID(objId);
	}
	

}
