package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumViewForumOperation extends MoodleReadOperation {

	public ForumViewForumOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String courseId) {
		super((short) 37, c, d, time, userId, objId);
		this.setObjectID(courseId);
	}
	
	public ForumViewForumOperation(ClientNode c, long time, String userId, String objId, String courseId) {
		super((short) 37, c, time, userId, objId);
		this.setObjectID(courseId);
	}
	
}
