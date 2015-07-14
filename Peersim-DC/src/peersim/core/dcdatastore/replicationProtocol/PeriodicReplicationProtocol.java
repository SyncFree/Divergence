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
import peersim.core.dcdatastore.util.DataObject;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public abstract class PeriodicReplicationProtocol implements EDProtocol, Initializable, DivergenceObservable, Cloneable {
	
	private static final String PAR_TRACK_DIVERGENTE = "divergencetracking";
	private static final String PAR_TRANSPORT = "transport";
	private static final String PAR_PERIOD = "period";
	//private static final String PAR_JITTER = "jitter";
	
	
	protected static int transportID;
	
	protected static long period;
	
	protected static boolean trackDivergence;
	
	private DivergenceMetrics divergenceMeasures;
	//protected static long jitter;
	
	public PeriodicReplicationProtocol(String name) {
		PeriodicReplicationProtocol.transportID = Configuration.lookupPid(Configuration.getString(name +"."+PAR_TRANSPORT));
		PeriodicReplicationProtocol.period = Configuration.getLong(name + "." + PAR_PERIOD);
		//PeriodicReplicationProtocol.jitter = Configuration.getLong(name + "." + PAR_JITTER, 0);
		PeriodicReplicationProtocol.trackDivergence = Configuration.contains(name + "." + PAR_TRACK_DIVERGENTE);
		if(PeriodicReplicationProtocol.trackDivergence)
			this.divergenceMeasures = new DivergenceMetrics();
	}

	public PeriodicReplicationProtocol clone() {
		PeriodicReplicationProtocol rrp = null;
		try {
			rrp = (PeriodicReplicationProtocol) super.clone();
			if(PeriodicReplicationProtocol.trackDivergence)
				rrp.divergenceMeasures = (DivergenceMetrics) this.divergenceMeasures.clone();
		} catch (CloneNotSupportedException e) {
			//Never happens
		}
		return rrp;
	}

	public void processEvent(Node node, int pid, Object event) {
		//System.err.println("node " + node.getID() + " handling: " + event.getClass().getCanonicalName());
		if(event instanceof ClientWriteOperation) {
			//System.err.println("Redirecting client write request to real protocol.");
			if (this.handleClientWriteRequest((ServerNode)node, pid, (ClientWriteOperation<?>) event)) {
				((PeriodicReplicationProtocol)DCCommonState.globalServer().getProtocol(pid)).handleClientWriteRequest((ServerNode)DCCommonState.globalServer(), pid, (ClientWriteOperation<?>) event);
			}
		} else if(event instanceof OperationPropagationEvent) {
			//System.err.println("Redirecting operation propagation request to real protocol.");
			this.handleServerPropagationRequest((ServerNode)node, pid, (OperationPropagationEvent) event);
		} else if(event instanceof ClientReadOperation) {
			this.handleClientReadRequest((ServerNode) node, pid, (ClientReadOperation) event);
		} else if(event instanceof NextPeriodicSync) {
			NextPeriodicSync nps = (NextPeriodicSync) event;
			this.nextCycle(nps.node, nps.protocolID);
			DCDataStoreSimulator.add(PeriodicReplicationProtocol.period, event, nps.node, nps.protocolID);
		}
	} 

	public void nextCycle(Node node, int protocolID) {
		//Syncronization time
		//System.err.println("@" + DCCommonState.getTime() + ", Node " + node.getID() + " is executing periodic sync.");
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
		Transport t = (Transport) node.getProtocol(PeriodicReplicationProtocol.transportID);
		for(int dc = 1; dc <= n; dc++) {
			if(dc != i ) {
				//System.err.println("Node " + node.getID() + " sending " + event.getClass().getCanonicalName() + " to node " + GeoReplicatedDatastoreNetwork.getServersForDC(dc)[0].getID());
				t.send(node, GeoReplicatedDatastoreNetwork.getServersForDC(dc)[0], event, pid);
			}
		}
	}
	
	public final void replyToClient(ServerNode node, int pid, ReadReply<?> reply) {	
		if(node.getIndex() < 0) return;
		
		if(PeriodicReplicationProtocol.trackDivergence) {
			String objectID = reply.getObjectID();
			DataObject<?,?> localData = node.read(objectID);
			if(localData != null)
				this.divergenceMeasures.addMeasure(localData.computeDivergence((DataObject<?,?>)DCCommonState.globalServer().read(objectID)));
			else if(DCCommonState.globalServer().read(objectID) != null)
				this.divergenceMeasures.addMeasure(((Integer)DCCommonState.globalServer().read(objectID).getMetadata()).doubleValue());
		}
		
		((Transport)node.getProtocol(PeriodicReplicationProtocol.transportID)).send(node, reply.getDestination(), reply, reply.getClientProtocolID());
	}
	
	public final void init(Node n, short pid) {
		DCDataStoreSimulator.add(PeriodicReplicationProtocol.period, new NextPeriodicSync(n, pid), n, pid);
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
