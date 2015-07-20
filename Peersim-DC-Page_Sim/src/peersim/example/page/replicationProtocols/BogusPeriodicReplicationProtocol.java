package peersim.example.page.replicationProtocols;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import peersim.core.CommonState;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.replicationProtocol.PeriodicReplicationProtocol;
import peersim.core.dcdatastore.serverEvents.MultipleOperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.example.page.replicationProtocols.events.PageUpdateOperation;
import peersim.example.page.replicationProtocols.events.MoodleReadOperation;
import peersim.example.page.replicationProtocols.events.MoodleWriteOperation;
import peersim.example.page.replicationProtocols.events.PageSimReadReply;
import peersim.example.page.replicationsProtocols.data.PageSim;
import moodleLogic.Course;
import moodleLogic.Directory;


public class BogusPeriodicReplicationProtocol extends
		PeriodicReplicationProtocol implements Cloneable {
	
	private List<PageUpdateOperation> operations;
	
	private Course c = new Course(0, 30);
	
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
		c = (Course) node.read(courseId);
		if (c == null) {
			// f**k
			System.err.println("[ Error ] No course "+ courseId + " found in DB." );
		}
		String objId;
		String[] tmp;
		
		
		switch(event.operationID()){
		case 29:
			//Folder add operation
			objId = op.getOperationId();
			
			c.DirAddOperation(userId, objId);
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
			objId = op.getOperationId();
			tmp = objId.split(",");
			
			c.ForumAddPostOperation(userId, tmp[0], tmp[1] );
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
			tmp = objId.split(",");
			
			c.ForumUpdatePostOperation(userId, tmp[0], tmp[1] );
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
		default:
			System.err.println("[ ERROR ] Unknown operationID (" + event.getObjectID() + "). [handleClientWriteRequest]");
		}
		
		return true;
	}

	@Override
	public void handleClientReadRequest(ServerNode node, int pid, ClientReadOperation event) {
		
		PageSim p = (PageSim) node.read(event.getObjectID());
		PageSimReadReply prr = new PageSimReadReply(event, p, CommonState.getTime());
		
		String userId = ((MoodleReadOperation) event).getUserId();
		String objId;
		
		switch(event.operationID()){
		case 32:
			//Folder view operation
			objId = event.getObjectID();
			
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
		default:
			System.err.println("[ ERROR ] Unknown operationID (" + event.getObjectID() + "). [handleClientWriteRequest]");
		}	
		
		this.replyToClient(node, prr.getClientProtocolID(), prr);
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
