package peersim.example.replicationProtocols.clients;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import peersim.core.CommonState;
import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.clientEventGenerators.BaseClientOperationGenerator;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperationGenerationEvent;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperationGenerator;
import peersim.example.replicationProtocols.events.CounterIncrementOperation;
import peersim.example.replicationProtocols.events.CounterReadOperation;

public class RandomCounterOperationGenerator extends BaseClientOperationGenerator implements
		ClientOperationGenerator {
	
	private double changeOfWrite;
	private int numberOfObjects;
	private String[] objectIdentifiers;
	private int maximumValueForIncrement;
	private long maximumTimeToGenerateOperations;
	private long roundLength;
	private int numberOfOperationsPerClientPerRound;
	
	private long nextGenerationEvent;
	
	public RandomCounterOperationGenerator() {
		super();
	}

	/**
	 * Expected file format for this particular generator (one value per line):
	 * Number of Objects to be Manipulated in the Simulation.
	 * Percentage (double) of write operations.
	 * Maximum argument of increment in write operations
	 * Maximum time up to which clients generate operations
	 * Duration of a round of client operations
	 * Number of operations per client per round
	 */
	
	public void init(String filename) {
		try{
			Scanner sc = new Scanner(new File(filename));
			this.numberOfObjects = Integer.parseInt(sc.nextLine());
			this.objectIdentifiers = new String[this.numberOfObjects];
			for(int i = 0; i < this.numberOfObjects; i++) {
				this.objectIdentifiers[i] = UUID.randomUUID().toString();
			}
			this.changeOfWrite = Double.parseDouble(sc.nextLine());
			this.maximumValueForIncrement = Integer.parseInt(sc.nextLine());
			this.maximumTimeToGenerateOperations = Long.parseLong(sc.nextLine());
			this.roundLength = Long.parseLong(sc.nextLine());
			this.numberOfOperationsPerClientPerRound = Integer.parseInt(sc.nextLine());
			sc.close();
		} catch (Exception e) {
			System.err.println("Error parsing configuration file for the operation generator: " + this.getClass().getCanonicalName());
			e.printStackTrace();
			System.exit(666);
		}
		
		this.nextGenerationEvent = 0;
	}

	public ClientOperationGenerationEvent hasMoreOperations() {
		if (this.nextGenerationEvent <= this.maximumTimeToGenerateOperations)
			return new ClientOperationGenerationEvent(this.nextGenerationEvent);
		else 
			return null;
	}

	public List<ClientOperation> getNextSetOfOperations() {
		List<ClientOperation> ops = new ArrayList<ClientOperation>();
		if(DCCommonState.getTime() > this.nextGenerationEvent) return ops;
		for (int i = 1; i <= GeoReplicatedDatastoreNetwork.numberDCs(); i ++) {
			for(int j = 0; j < GeoReplicatedDatastoreNetwork.sizeClients() / GeoReplicatedDatastoreNetwork.numberDCs(); j++) {
				ClientNode client = GeoReplicatedDatastoreNetwork.getClient(i, j);
				for(int k = 0; k < this.numberOfOperationsPerClientPerRound; k ++) {
					long time = DCCommonState.getTime() + DCCommonState.r.nextLong(this.roundLength);
					boolean isWriteOp = CommonState.r.nextDouble() <= this.changeOfWrite;
					String key = this.objectIdentifiers[CommonState.r.nextInt(this.objectIdentifiers.length)];
					ClientOperation newOp = null;
					if(isWriteOp) {
						int increment = CommonState.r.nextInt(this.maximumValueForIncrement);
						newOp = new CounterIncrementOperation(client, time);
						((CounterIncrementOperation)newOp).setValue(increment);
					} else {
						newOp = new CounterReadOperation(client, time);
						
					}
					newOp.setDestination(client.getServer(0));
					newOp.setClientProtocolID(RandomCounterOperationGenerator.clientProtocolID);
					newOp.setObjectID(key);
					ops.add(newOp);
				}
			}
		}
		Collections.sort(ops, new Comparator<ClientOperation>() {

			public int compare(ClientOperation o1, ClientOperation o2) {
				if(o1.getTimeOfCreation() == o2.getTimeOfCreation())
					return o1.getClient().getIndex() - o2.getClient().getIndex();
				else
					return (int) (o1.getTimeOfCreation() - o2.getTimeOfCreation());
			}
		});
		this.nextGenerationEvent = DCCommonState.getTime() + this.roundLength;
		return ops;
	}

}
