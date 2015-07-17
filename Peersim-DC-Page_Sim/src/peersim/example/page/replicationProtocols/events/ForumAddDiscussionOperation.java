package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ForumAddDiscussionOperation extends MoodleWriteOperation<Integer> {

	public ForumAddDiscussionOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 34, c, d, time, userId);
		this.setObjectID(objId);
	}
	
	public ForumAddDiscussionOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 34, c, time, userId);
		this.setObjectID(objId);

	}
}
