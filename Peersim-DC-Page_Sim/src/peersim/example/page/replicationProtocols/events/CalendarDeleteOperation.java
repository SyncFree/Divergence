package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class CalendarDeleteOperation extends MoodleWriteOperation<Integer> {
	
	public CalendarDeleteOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 13, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CalendarDeleteOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 13, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	

}
