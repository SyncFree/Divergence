package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CalendarUpdateOperation extends MoodleWriteOperation<Integer> {
	
	public CalendarUpdateOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 14, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CalendarUpdateOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 14, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
