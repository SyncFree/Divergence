package peersim.core.dcdatastore.replicationProtocol;

import java.util.Iterator;

import peersim.config.Configuration;
import peersim.core.Node;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.clientEventGenerators.ReadReply;
import peersim.core.dcdatastore.initializers.databaseinit.DatabaseInitializable;
import peersim.core.dcdatastore.observers.dbstate.DatabaseObservable;
import peersim.core.dcdatastore.observers.divergence.DivergenceObservable;
import peersim.core.dcdatastore.replicationProtocol.divergenceControl.DivergenceMetrics;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.SimpleOperationPropagationEvent;
import peersim.core.dcdatastore.util.DataObject;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public abstract class ReactiveReplicationProtocol implements EDProtocol, DivergenceObservable, DatabaseInitializable, DatabaseObservable, Cloneable {
	
	private static final String PAR_TRACK_DIVERGENTE = "divergencetracking";
	private static final String PAR_TRANSPORT = "transport";
	
	protected static int transportID;
	
	protected static boolean trackDivergence;
	
	private DivergenceMetrics divergenceMeasures;
	
	public ReactiveReplicationProtocol(String name) {
		transportID = Configuration.lookupPid(Configuration.getString(name +"."+PAR_TRANSPORT));
		ReactiveReplicationProtocol.trackDivergence = Configuration.contains(name + "." + PAR_TRACK_DIVERGENTE);
		if(ReactiveReplicationProtocol.trackDivergence)
			this.divergenceMeasures = new DivergenceMetrics();
	}

	public ReactiveReplicationProtocol clone() {
		ReactiveReplicationProtocol rrp = null;
		try {
			rrp = (ReactiveReplicationProtocol) super.clone();
		} catch (CloneNotSupportedException e) {
			//Never happens
		}
		return rrp;
	}

	public void processEvent(Node node, int pid, Object event) {
		if(event instanceof ClientWriteOperation) {
			if (this.handleClientWriteRequest((ServerNode)node, pid, (ClientWriteOperation<?>) event)) {
				((ReactiveReplicationProtocol)DCCommonState.globalServer().getProtocol(pid)).handleClientWriteRequest((ServerNode)DCCommonState.globalServer(), pid, (ClientWriteOperation<?>) event);
				this.propagateToAllDCs((ServerNode) node, pid, new SimpleOperationPropagationEvent((ServerNode) node, (ClientWriteOperation<?>) event));
			}
		} else if(event instanceof OperationPropagationEvent) {
			this.handleServerPropagationRequest((ServerNode)node, pid, (OperationPropagationEvent) event);
		} else if(event instanceof ClientReadOperation) {
			this.handleClientReadRequest((ServerNode) node, pid, (ClientReadOperation) event);
		}
	}

	public abstract boolean handleClientWriteRequest(ServerNode node, int pid, ClientWriteOperation<?> event);
	
	public abstract void handleClientReadRequest(ServerNode node, int pid, ClientReadOperation event);
	
	public abstract void handleServerPropagationRequest(ServerNode node, int pid, OperationPropagationEvent event);
	
	public final void propagateToAllDCs(ServerNode node, int pid, OperationPropagationEvent event) {
		if(node.getIndex() < 0) //This is the master node and should not issue messages
			return;
		
		short i = node.getDC();
		int n = GeoReplicatedDatastoreNetwork.numberDCs();
		Transport t = (Transport) node.getProtocol(ReactiveReplicationProtocol.transportID);
		for(int dc = 1; dc <= n; dc++) {
			if(dc != i ) {
				t.send(node, GeoReplicatedDatastoreNetwork.getServersForDC(dc)[0], event, pid);
			}
		}
	}
	
	public final void replyToClient(ServerNode node, int pid, ReadReply<?> reply) {	
		if(node.getIndex() < 0) return;
		
		if(ReactiveReplicationProtocol.trackDivergence) {
			String objectID = reply.getObjectID();
			DataObject<?,?> localData = node.read(objectID);
			if(localData != null)
				this.divergenceMeasures.addMeasure(localData.computeDivergence((DataObject<?,?>)DCCommonState.globalServer().read(objectID)));
			else if(DCCommonState.globalServer().read(objectID) != null)
				this.divergenceMeasures.addMeasure(((Integer)DCCommonState.globalServer().read(objectID).getMetadata()).doubleValue());
		}
		
		((Transport)node.getProtocol(PeriodicReplicationProtocol.transportID)).send(node, reply.getDestination(), reply, reply.getClientProtocolID());
	}

	public double getMinDivergence() {
		return this.divergenceMeasures.getMin();
	}

	public double getMaxDivergence() {
		return this.divergenceMeasures.getMax();
	}

	public double getAverageDivergence() {
		return this.divergenceMeasures.getAverage();
	}

	public Iterator<Double> getDivergenceMeasures() {
		return this.divergenceMeasures.getMeasures();
	}

	public int getDivergenceMeasuresCount() {
		return this.divergenceMeasures.getMeasuresCount();
	}

	public void resetDivergenceMetrics() {
		this.divergenceMeasures.reset();
	}
	
	public void storeObject(ServerNode node, String key, DataObject<?,?> object) {
		node.write(key, object);
	}
}
