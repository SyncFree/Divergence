package peersim.example.page.replicationProtocols.clients;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import log.formats.MoodleOperation;
import log.formats.Operation;
import log.formats.model.MOODLE_OP;
import log.readers.LogByTime;
import log.readers.SimpleLog;
import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.BaseClientOperationGenerator;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperation;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperationGenerationEvent;
import peersim.core.dcdatastore.clientEventGenerators.ClientOperationGenerator;
import peersim.example.page.replicationProtocols.events.*;
import peersim.example.page.replicationsProtocols.data.*;


public class RandomPageOperationGenerator extends BaseClientOperationGenerator implements ClientOperationGenerator {	
	private SimpleLog<MOODLE_OP> log;
	private int timeVal;
	
	public RandomPageOperationGenerator() {
		super();
		timeVal = 0;
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
			log = new SimpleLog<MOODLE_OP>(filename, MoodleOperation.getFactory());
			
		} catch (Exception e) {
			System.err.println("Error : " + this.getClass().getCanonicalName());
			e.printStackTrace();
			System.exit(666);
		}
	}

	public ClientOperationGenerationEvent hasMoreOperations() {
		if(log.hasNext()) 
			return new ClientOperationGenerationEvent(DCCommonState.getTime());
		else
			return null;
	}

	public List<ClientOperation> getNextSetOfOperations() {
		List<ClientOperation> ops = new ArrayList<ClientOperation>();
		ClientOperation newOp = null;
		String userId;
		String operationType;
		int pageId;
		
		long startOfTime = -1;
		
		Map<String, ClientNode> clientMap = new HashMap<String, ClientNode>();
		int lastClientUsed = -1;
		
		while(log.hasNext()) {
			Operation<MOODLE_OP> op = log.next();
			userId = op.getAttributeByName(MOODLE_OP.REQUESTER_ID);
			operationType = op.getAttributeByName(MOODLE_OP.OPERATION).replaceAll("\"", "");
			
			long time = op.getTimestamp();
			if(startOfTime == -1) {
				startOfTime = time;
			}
			time -= startOfTime;
			if(!clientMap.containsKey(userId)) {
				lastClientUsed++;
				if (lastClientUsed == GeoReplicatedDatastoreNetwork.sizeClients())
					lastClientUsed = 0;
				clientMap.put(userId, GeoReplicatedDatastoreNetwork.getClient(lastClientUsed));
			}
			ClientNode client = clientMap.get(userId);
			
			switch(operationType) {
				case "page view":
					
					pageId = Integer.parseInt(op.getAttributeByName(MOODLE_OP.INFO)); 
					newOp = new PageViewOperation(client, time, pageId);
				break;
				case "page add":
					pageId = Integer.parseInt(op.getAttributeByName(MOODLE_OP.INFO)); 
					newOp = new PageAddOperation(client, time, pageId);
				break;
				case "page update":
					pageId = Integer.parseInt(op.getAttributeByName(MOODLE_OP.INFO)); 
					newOp = new PageUpdateOperation(client, time, pageId);
					
				break;
			default:
					//System.err.println("Error: This operation \"" +  operationType + "\" doesn't exist.");
			}
			if(newOp != null){
				ops.add(newOp);
				System.out.println(newOp.getObjectID());
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
		return ops;
	}

}

