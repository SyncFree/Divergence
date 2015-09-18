package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class CalendarAddOperation extends MoodleWriteOperation<Integer> {
	public CalendarAddOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 12, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public CalendarAddOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 12, c, time, userId, objId);
		this.setObjectID(courseId);
	}
}
