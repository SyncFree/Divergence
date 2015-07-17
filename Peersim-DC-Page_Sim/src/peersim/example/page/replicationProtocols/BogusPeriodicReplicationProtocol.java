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


public class BogusPeriodicReplicationProtocol extends
		PeriodicReplicationProtocol implements Cloneable {
	
	private static Course c = new Course(0, 30);
	
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
		
		String userId = ((MoodleWriteOperation) event).getUserId();
		String objId;
		String[] tmp;
		
		switch(event.operationID()){
		case 29:
			//resource add operation
			objId = event.getObjectID();
			
			c.DirAddOperation(userId, objId);
			break;
		case 31:
			//resource update operation
			objId = event.getObjectID();
			
			c.DirEditOperation(userId, objId);
			break;
		case 33:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumAddDiscussionOperation(userId, objId);
			break;
		case 34:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumAddOperation(userId, objId);
			break;
		
		case 35:
			//resource update operation
			objId = event.getObjectID();
			tmp = objId.split(",");
			
			c.ForumAddPostOperation(userId, tmp[0], tmp[1] );
			break;
		case 37:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumSubscribeOperation(userId, objId);
			break;
		case 38:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumUnsubscribeOperation(userId, objId);
			break;
		case 39:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumUpdateDiscussionOperation(userId, objId);
			break;
		case 40:
			//resource update operation
			objId = event.getObjectID();
			
			c.ForumUpdateOperation(userId, objId);
			break;
		case 41:
			//resource update operation
			objId = event.getObjectID();
			tmp = objId.split(",");
			
			c.ForumUpdatePostOperation(userId, tmp[0], tmp[1] );
			break;
			
		case 56:
			//resource add operation
			objId = event.getObjectID();
			
			c.ResourceAddOperation(userId, objId);
			break;
		case 58:
			//resource update operation
			objId = event.getObjectID();
			
			c.ResourceEditOperation(userId, objId);
			break;
		case 60:
			//resource view operation
			objId = event.getObjectID();
			
			c.ResourceViewOperation(userId, objId);
			break;
		case 73:
			//page add operation
			//PageAddOperation op = (PageAddOperation) event;
			
			objId = event.getObjectID();
			
			c.PageAddOperation(userId, objId);
			break;
		case 74:
			//page view operation
			objId = event.getObjectID();
			
			c.PageViewOperation(userId, objId);
			break;
		case 75:
			//page update operation
			objId = event.getObjectID();
			
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
	public void handleServerPropagationRequest(ServerNode node, int pid,
			OperationPropagationEvent event) {
		Iterator<ClientWriteOperation<?>> ite = ((MultipleOperationPropagationEvent) event).getClientOperations();
		while(ite.hasNext()) {
			//
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
