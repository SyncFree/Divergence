package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class DirUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public DirUpdateOperation(ClientNode c, long time, String userId, String objId) {
		super((short) 31, c, time, userId);
		this.setObjectID(objId);
	}
	
	public DirUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId) {
		super((short) 31, c, d, time, userId);
		this.setObjectID(objId);
	}
	

}
