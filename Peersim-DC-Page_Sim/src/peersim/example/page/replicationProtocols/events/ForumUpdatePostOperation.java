package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;

public class ForumUpdatePostOperation extends MoodleWriteOperation<Integer> {
	private String disId;
	private String postId;

	
	public ForumUpdatePostOperation(ClientNode c, long time, String userId, String objId, String disId, String postId, String courseId) {
		super((short) 41, c, time, userId, objId);
		this.setObjectID(courseId);
		this.disId = disId;
		this.postId = postId;

	}
	
	public ForumUpdatePostOperation(ClientNode c, ServerNode d, long time, String userId, String objId, String disId, String postId, String courseId) {
		super((short) 41, c, d, time, userId, objId);
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
