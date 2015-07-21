package peersim.example.page.replicationProtocols.events;

import moodleLogic.Course;

import peersim.core.dcdatastore.clientEventGenerators.BaseReadReply;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;

public class CourseSimReadReply extends BaseReadReply<Course> {

	public CourseSimReadReply(ClientReadOperation req, Course reply, long timestamp) {
		super(req, reply, timestamp);
	}

}
