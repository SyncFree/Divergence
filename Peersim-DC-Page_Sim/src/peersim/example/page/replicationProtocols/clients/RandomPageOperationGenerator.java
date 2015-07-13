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
	private Scanner sc;
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
			LogByTime log = new LogByTime(filename, MoodleOperation.getFactory());
			
		} catch (Exception e) {
			System.err.println("Error : " + this.getClass().getCanonicalName());
			e.printStackTrace();
			System.exit(666);
		}
	}

	public ClientOperationGenerationEvent hasMoreOperations() {
		if(sc.hasNext()) 
			return new ClientOperationGenerationEvent(DCCommonState.getTime());
		else
			return null;
	}

	public List<ClientOperation> getNextSetOfOperations() {
		List<ClientOperation> ops = new ArrayList<ClientOperation>();
		ClientOperation newOp = null;
		
		long startOfTime = -1;
		
		Map<String, ClientNode> clientMap = new HashMap<String, ClientNode>();
		int lastClientUsed = -1;
		
		while(sc.hasNext()) {
			String[] line = sc.nextLine().split("\t");
			//String courseId = line[0];
			//long time = Long.parseLong(line[1]);
			long time = ++timeVal;
			if(startOfTime == -1) {
				startOfTime = time;
			}
			time -= startOfTime;
			if(!clientMap.containsKey(line[3])) {
				lastClientUsed++;
				if (lastClientUsed == GeoReplicatedDatastoreNetwork.sizeClients())
					lastClientUsed = 0;
				clientMap.put(line[3], GeoReplicatedDatastoreNetwork.getClient(lastClientUsed, lastClientUsed));
			}
			ClientNode client = clientMap.get(line[3]);
			String operationType = line[4].replaceAll("\"", "");
			switch(operationType) {
				case "page view":
					int pageId = Integer.parseInt(line[5]);
					newOp = new PageViewOperation(client, time, pageId);
				break;
				case "page add":
					pageId = Integer.parseInt(line[5]);
					newOp = new PageAddOperation(client, time, pageId);
				break;
				case "page update":
					pageId = Integer.parseInt(line[5]);
					newOp = new PageUpdateOperation(client, time, pageId);
				break;
			default:
					//System.err.println("Error: This operation \"" +  operationType + "\" doesn't exist.");
			}
			if(newOp != null)
				ops.add(newOp);
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

