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

import peersim.example.page.replicationProtocols.events.MoodleOperation;

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
	public boolean handleClientWriteRequest(ServerNode node, int pid,
			ClientWriteOperation<?> event) {
		
		String userId = ((MoodleOperation) event).getUserId();
		String objId;
		
		switch(event.operationID()){
		case 73:
			//page add operation
			System.out.println("add");
			//PageAddOperation op = (PageAddOperation) event;
			
			objId = event.getObjectID();
			
			c.PageAddOperation(userId, objId);
			break;
		case 74:
			//page view operation
			System.out.println("view");
			objId = event.getObjectID();
			
			c.PageViewOperation(userId, objId);
			break;
		case 75:
			//page update operation
			System.out.println("update");
			objId = event.getObjectID();
			
			c.PageEditOperation(userId, objId);
			break;
		default:
			System.err.println("[ ERROR ] Unknown operationID (" + event.getObjectID() + "). [handleClientWriteRequest]");
		}
		
		return true;
	}

	@Override
	public void handleClientReadRequest(ServerNode node, int pid,
			ClientReadOperation event) {
		PageSim p = (PageSim) node.read(event.getObjectID());
		PageSimReadReply prr = new PageSimReadReply(event, p, CommonState.getTime());
		this.replyToClient(node, prr.getClientProtocolID(), prr);
	}

	@Override
	public void handleServerPropagationRequest(ServerNode node, int pid,
			OperationPropagationEvent event) {
		Iterator<ClientWriteOperation<?>> ite = ((MultipleOperationPropagationEvent) event).getClientOperations();
		while(ite.hasNext()) {
			PageUpdateOperation operation = (PageUpdateOperation) ite.next();
			PageSim p = (PageSim) node.read(operation.getObjectID());
			if(p == null) { 
				p = new PageSim(operation.operationID());
				node.write(operation.getObjectID(), p);
			}
			p.incValue();
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
