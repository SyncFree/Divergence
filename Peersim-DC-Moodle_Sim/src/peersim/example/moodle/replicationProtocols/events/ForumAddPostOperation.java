package peersim.example.moodle.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;


import peersim.core.dcdatastore.ServerNode;

public class ForumAddPostOperation extends MoodleWriteOperation<Integer> {
	private String disId;
	private String postId;

	public ForumAddPostOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String disId, String postId, String courseId) {
		super((short) 35, c, d, time, userId, objId);
		this.setObjectID(courseId);
		this.disId = disId;
		this.postId = postId;
	}
	
	public ForumAddPostOperation(ClientNode c, long time, String userId, String objId, String disId, String postId, String courseId) {
		super((short) 35, c, time, userId, objId);
		this.setObjectID(courseId);
		this.disId = disId;
		this.postId = postId;
	}
	
	public String getDiscussionId(){
		return this.disId;
	}
	
	public String getPostId(){
		return this.postId;
	}
}
