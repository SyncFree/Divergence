package peersim.core.dcdatastore.replicationProtocol;

import java.util.Iterator;

import peersim.config.Configuration;
import peersim.core.Node;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.DCDataStoreSimulator;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientReadOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;
import peersim.core.dcdatastore.clientEventGenerators.ReadReply;
import peersim.core.dcdatastore.controls.Initializable;
import peersim.core.dcdatastore.controls.NextPeriodicSync;
import peersim.core.dcdatastore.observers.divergence.DivergenceObservable;
import peersim.core.dcdatastore.replicationProtocol.divergenceControl.DivergenceMetrics;
import peersim.core.dcdatastore.serverEvents.OperationPropagationEvent;
import peersim.core.dcdatastore.serverEvents.SimpleOperationPropagationEvent;
import peersim.core.dcdatastore.util.DataObject;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public abstract class HybridReplicationProtocol implements EDProtocol, Initializable, DivergenceObservable, Cloneable {
	
	private static final String PAR_TRACK_DIVERGENTE = "divergencetracking";
	private static final String PAR_TRANSPORT = "transport";
	private static final String PAR_PERIOD = "period";
	//private static final String PAR_JITTER = "jitter";
	
	protected static int transportID;
	protected static long period;
	protected static boolean trackDivergence;
	
	private DivergenceMetrics divergenceMeasures;
	//protected static long jitter;
	
	public HybridReplicationProtocol(String name) {
		HybridReplicationProtocol.transportID = Configuration.lookupPid(Configuration.getString(name +"."+PAR_TRANSPORT));
		HybridReplicationProtocol.period = Configuration.getLong(name + "." + PAR_PERIOD);
		//HybridReplicationProtocol.jitter = Configuration.getLong(name + "." + PAR_JITTER, 0);
		HybridReplicationProtocol.trackDivergence = Configuration.contains(name + "." + PAR_TRACK_DIVERGENTE);
		if(HybridReplicationProtocol.trackDivergence)
			this.divergenceMeasures = new DivergenceMetrics();
	}

	public HybridReplicationProtocol clone() {
		HybridReplicationProtocol rrp = null;
		try {
			rrp = (HybridReplicationProtocol) super.clone();
			if(HybridReplicationProtocol.trackDivergence)
				rrp.divergenceMeasures = (DivergenceMetrics) this.divergenceMeasures.clone();
		} catch (CloneNotSupportedException e) {
			//Never happens
		}
		return rrp;
	}

	public void processEvent(Node node, int pid, Object event) {
		if(event instanceof ClientWriteOperation) {
			if (this.handleClientWriteRequest((ServerNode)node, pid, (ClientWriteOperation<?>) event)) {
				 ((HybridReplicationProtocol)DCCommonState.globalServer().getProtocol(pid)).handleClientWriteRequest((ServerNode) DCCommonState.globalServer(), pid, (ClientWriteOperation<?>) event);
				if(node.getID() >= 0) this.propagateToAllDCs((ServerNode) node, pid, new SimpleOperationPropagationEvent((ServerNode) node, (ClientWriteOperation<?>) event));
			}
		} else if(event instanceof OperationPropagationEvent) {
			this.handleServerPropagationRequest((ServerNode)node, pid, (OperationPropagationEvent) event);
		} else if(event instanceof ClientReadOperation) {
			this.handleClientReadRequest((ServerNode) node, pid, (ClientReadOperation) event);
		} else if(event instanceof NextPeriodicSync) {
			NextPeriodicSync nps = (NextPeriodicSync) event;
			this.nextCycle(nps.node, nps.protocolID);
			DCDataStoreSimulator.add(HybridReplicationProtocol.period, event, nps.node, nps.protocolID);
		}
	}

	public void nextCycle(Node node, int protocolID) {
		//Syncronization time
		OperationPropagationEvent delta = this.getStateToPropagate((ServerNode) node);
		this.propagateToAllDCs((ServerNode) node, protocolID, delta);
	}
	
	public abstract boolean handleClientWriteRequest(ServerNode node, int pid, ClientWriteOperation<?> event);
	
	public abstract void handleClientReadRequest(ServerNode node, int pid, ClientReadOperation event);
	
	public abstract void handleServerPropagationRequest(ServerNode node, int pid, OperationPropagationEvent event);
	
	public abstract OperationPropagationEvent getStateToPropagate(ServerNode node);

	public final void propagateToAllDCs(ServerNode node, int pid, OperationPropagationEvent event) {
		if(node.getIndex() < 0) //This is the master node and should not issue messages
			return;
		
		short i = node.getDC();
		int n = GeoReplicatedDatastoreNetwork.numberDCs();
		Transport t = (Transport) node.getProtocol(HybridReplicationProtocol.transportID);
		for(int dc = 1; dc <= n; dc++) {
			if(dc != i ) {
				t.send(node, GeoReplicatedDatastoreNetwork.getServersForDC(dc)[0], event, pid);
			}
		}
	}
	
	public final void replyToClient(ServerNode node, int pid, ReadReply<?> reply) {	
		if(node.getIndex() < 0) return; //Master node does nothing.
		
		if(HybridReplicationProtocol.trackDivergence) {
			String objectID = reply.getObjectID();
			DataObject<?,?> localData = node.read(objectID);
			if(localData != null)
				this.divergenceMeasures.addMeasure(localData.computeDivergence((DataObject<?,?>)DCCommonState.globalServer().read(objectID)));
			else
				this.divergenceMeasures.addMeasure(((Integer)DCCommonState.globalServer().read(objectID).getMetadata()).doubleValue());
		}
			
		((Transport)node.getProtocol(HybridReplicationProtocol.transportID)).send(node, reply.getDestination(), reply, reply.getClientProtocolID());
	}

	public final void init(Node n, short pid) {
		DCDataStoreSimulator.add(HybridReplicationProtocol.period, new NextPeriodicSync(n, pid), n, pid);
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
	
}
