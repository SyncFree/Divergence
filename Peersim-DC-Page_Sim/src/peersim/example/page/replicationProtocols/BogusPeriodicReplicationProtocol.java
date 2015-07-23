package peersim.example.page.replicationProtocols;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import peersim.core.CommonState;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.initializers.databaseinit.DatabaseInitializable;
import peersim.core.dcdatastore.replicationProtocol.PeriodicReplicationProtocol;
import peersim.core.dcdatastore.serverEvents.MultipleOperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.core.dcdatastore.util.DataObject;
import peersim.example.page.replicationProtocols.events.PageUpdateOperation;
import peersim.example.page.replicationProtocols.events.MoodleReadOperation;
import peersim.example.page.replicationProtocols.events.MoodleWriteOperation;
import peersim.example.page.replicationProtocols.events.CourseSimReadReply;
import peersim.example.page.replicationProtocols.events.ForumAddPostOperation;
import peersim.example.page.replicationProtocols.events.ForumUpdatePostOperation;
import peersim.example.replicationProtocols.events.CounterReadReply;
import moodleLogic.Course;
import moodleLogic.Directory;


public class BogusPeriodicReplicationProtocol extends
		PeriodicReplicationProtocol implements Cloneable {
	
	private List<PageUpdateOperation> operations;
	
	public BogusPeriodicReplicationProtocol(String name) {
		super(name);
		this.operations = new ArrayList<PageUpdateOperation>();
	}
	
	public BogusPeriodicReplicationProtocol clone() {
		BogusPeriodicReplicationProtocol bprp = null;
		bprp = (BogusPeriodicReplicationProtocol) super.clone();
		bprp.operations = new ArrayList<PageUpdateOperation>();
		return bprp;
	}

	@Override
	public boolean handleClientWriteRequest(ServerNode node, int pid, ClientWriteOperation<?> event) {

		MoodleWriteOperation<?> op = (MoodleWriteOperation<?>) event;
		String userId = ((MoodleWriteOperation<?>) event).getUserId();
		String courseId = event.getObjectID();
		Course c = (Course) node.read(courseId);
		if (c == null) {
			// f**k
			System.err.println("[ Error ] No course "+ courseId + " found in DB." );
		}
		String objId;
		String[] tmp;
		
		
		switch(event.operationID()){
		case 1:
			//Folder add operation
			objId = op.getOperationId();
			
			c.AssignmentAddOperation(userId, objId);
			break;
		case 2:
			//Folder add operation
			objId = op.getOperationId();
			
			c.AssignmentUpdateGradesOperation(userId, objId);
			break;
		case 3:
			//Folder add operation
			objId = op.getOperationId();
			
			c.AssignmentUpdateOperation(userId, objId);
			break;
		case 4:
			//Folder add operation
			objId = op.getOperationId();
			
			c.AssignmentUploadOperation(userId, objId);
			break;
		case 9:
			//Folder add operation
			objId = op.getOperationId();
			
			c.BlogAddOperation(userId, objId);
			break;
		case 100:
			//Folder add operation
			objId = op.getOperationId();
			
			c.BlogEditOperation(userId, objId);
			break;			
		case 10:
			//Folder update operation
			objId = op.getOperationId();
			
			c.BlogDeleteOperation(userId, objId);
			break;
		case 12:
			//Folder add operation
			objId = op.getOperationId();
			
			c.CalendarAddOperation(userId, objId);
			break;
		case 13:
			//Folder update operation
			objId = op.getOperationId();
			
			c.CalendarDeleteOperation(userId, objId);
			break;
		case 14:
			//Folder update operation
			objId = op.getOperationId();
			
			c.CalendarEditOperation(userId, objId);
			break;
		case 22:
			//Folder update operation
			objId = op.getOperationId();
			
			c.CourseAddModuleOperation(userId, objId);
			break;
		case 25:
			//Folder update operation
			objId = op.getOperationId();
			
			c.CourseRoleAssignOperation(userId, objId);
			break;
		case 29:
			//Folder add operation
			objId = op.getOperationId();
			
			c.CourseRoleUnassignOperation(userId, objId);
			break;
		case 31:
			//Folder update operation
			objId = op.getOperationId();
			
			c.DirEditOperation(userId, objId);
			break;
		case 33:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumAddDiscussionOperation(userId, objId);
			break;
		case 34:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumAddOperation(userId, objId);
			break;
		
		case 35:
			//resource update operation
			ForumAddPostOperation fap_op = (ForumAddPostOperation) op;

			c.ForumAddPostOperation(userId, fap_op.getDiscussionId(), fap_op.getPostId() );
			break;
		case 37:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumSubscribeOperation(userId, objId);
			break;
		case 38:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumUnsubscribeOperation(userId, objId);
			break;
		case 39:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumUpdateDiscussionOperation(userId, objId);
			break;
		case 40:
			//resource update operation
			objId = op.getOperationId();
			
			c.ForumUpdateOperation(userId, objId);
			break;
		case 41:
			//resource update operation
			objId = op.getOperationId();
			
			ForumUpdatePostOperation fup_op = (ForumUpdatePostOperation) op;
			c.ForumUpdatePostOperation(userId, fup_op.getDiscussionId(), fup_op.getPostId() );
			break;
		case 46:
			//resource update operation
			objId = op.getOperationId();
			
			c.QuizAttemptOperation(userId, objId);
			break;
		case 47:
			//resource update operation
			objId = op.getOperationId();
			
			c.QuizCloseAttemptOperation(userId, objId);
			break;
		case 48:
			//resource update operation
			objId = op.getOperationId();
			
			c.QuizContinueAttemptOperation(userId, objId);
			break;
		case 49:
			//resource update operation
			objId = op.getOperationId();
			
			c.QuizEditOperation(userId, objId);
			break;
		case 56:
			//resource add operation
			objId = op.getOperationId();
			
			c.ResourceAddOperation(userId, objId);
			break;
		case 58:
			//resource update operation
			objId = op.getOperationId();
			
			c.ResourceEditOperation(userId, objId);
			break;
		case 60:
			//resource view operation
			objId = op.getOperationId();
			
			c.ResourceViewOperation(userId, objId);
			break;
		case 61:
			//resource view operation
			objId = op.getOperationId();
			
			c.UrlAddOperation(userId, objId);
			break;
		case 62:
			//resource view operation
			objId = op.getOperationId();
			
			c.UrlDeleteOperation(userId, objId);
			break;
		case 73:
			//page add operation
			//PageAddOperation op = (PageAddOperation) event;
			
			objId = op.getOperationId();
			
			c.PageAddOperation(userId, objId);
			break;
		case 74:
			//page view operation
			objId = op.getOperationId();
			
			c.PageViewOperation(userId, objId);
			break;
		case 75:
			//page update operation
			objId = op.getOperationId();
			
			c.PageEditOperation(userId, objId);
			break;
			
		case 777:
			// We should put something to know which operation is it.
			System.err.println("[ ERROR ] Operation Not Yet Implemented (" + event.getObjectID() + "). [handleClientWriteRequest]");
			break;
		default:
			System.err.println("[ ERROR ] Unknown operationID (" + event.operationID() + "). [handleClientWriteRequest]");
		}
		
		return true;
	}

	@Override
	public void handleClientReadRequest(ServerNode node, int pid, ClientReadOperation event) {
		
		MoodleReadOperation op = (MoodleReadOperation) event;
		
		String userId = ((MoodleReadOperation) event).getUserId();
		String courseId = event.getObjectID();
		String objId;
		
		Course c = (Course) node.read(courseId);
		if (c == null) {
			// f**k
			System.err.println("[ Error ] No course "+ courseId + " found in DB." );
		}
		CourseSimReadReply crr = new CourseSimReadReply(event, c, CommonState.getTime());

		switch(event.operationID()){
		case 32:
			//Folder view operation
			objId = op.getOperationId();
			
			c.DirViewOperation(userId, objId);
			
			break;
		case 60:
			//resource view operation
			objId = event.getObjectID();
			
			c.ResourceViewOperation(userId, objId);
			break;
		case 74:
			//page view operation
			objId = event.getObjectID();
			
			c.PageViewOperation(userId, objId);
			break;
		case 666:
			objId = event.getObjectID();
			
			c.GenericView(objId);
			break;
		default:
			System.err.println("[ ERROR ] Unknown operationID (" + event.operationID() + "). [handleClientReadRequest]");
		}	
		this.replyToClient(node, crr.getClientProtocolID(), crr);
	}

	@Override
	public void handleServerPropagationRequest(ServerNode node, int pid, OperationPropagationEvent event) {
		Iterator<ClientWriteOperation<?>> ite = ((MultipleOperationPropagationEvent) event).getClientOperations();
		while(ite.hasNext()) {
			// We apply the same function that processes the Client-Server operations
			// But wont it send the messages again to the other replicas. In the 
			// handleClientWrite doesn't seem so...
			handleClientWriteRequest(node, pid, ite.next());
		}
	}

	@Override
	public OperationPropagationEvent getStateToPropagate(ServerNode node) {
		OperationPropagationEvent ope = new MultipleOperationPropagationEvent(node);
		for(ClientWriteOperation<?> op: this.operations)
			ope.addOperation(op);
		return ope;
	}


}
