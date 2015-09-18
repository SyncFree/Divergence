package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.ServerNode;

public class PageUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public PageUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 75, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public PageUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 75, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
