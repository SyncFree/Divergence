package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ForumSubscribeOperation extends MoodleWriteOperation<Integer> {

	public ForumSubscribeOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 37, c, d, time, userId);
		this.setObjectID(objId);
	}
	
	public ForumSubscribeOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 37, c, time, userId);
		this.setObjectID(objId);
		}
	
}
