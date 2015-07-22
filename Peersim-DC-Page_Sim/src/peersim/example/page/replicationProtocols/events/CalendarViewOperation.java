package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class CalendarViewOperation extends MoodleReadOperation {
	
	public CalendarViewOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 15, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CalendarViewOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 15, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
}
