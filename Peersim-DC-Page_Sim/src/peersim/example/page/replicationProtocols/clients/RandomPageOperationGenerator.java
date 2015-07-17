package peersim.example.page.replicationProtocols.clients;



import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import log.formats.MoodleOperation;
import log.formats.Operation;
import log.formats.model.MOODLE_OP;

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
	
	public RandomPageOperationGenerator() {
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
		String objId;
		
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
					
					objId = op.getAttributeByName(MOODLE_OP.INFO); 
					newOp = new PageViewOperation(client, time, objId, userId);
				break;
				case "page add":
					objId = op.getAttributeByName(MOODLE_OP.INFO); 
					newOp = new PageAddOperation(client, time, objId, userId);
				break;
				case "page update":
					objId = op.getAttributeByName(MOODLE_OP.INFO); 
					newOp = new PageUpdateOperation(client, time, objId, userId);
				break;
			default:
					//System.err.println("Error: This operation \"" +  operationType + "\" doesn't exist.");
			}
			if(newOp != null){
				newOp.setClientProtocolID(RandomPageOperationGenerator.clientProtocolID);
				newOp.setDestination(client.getServer(0));
				ops.add(newOp);
			}
		}
		
		/*Collections.sort(ops, new Comparator<ClientOperation>() {

			public int compare(ClientOperation o1, ClientOperation o2) {
				if(o1.getTimeOfCreation() == o2.getTimeOfCreation())
					return o1.getClient().getIndex() - o2.getClient().getIndex();
				else
					return (int) (o1.getTimeOfCreation() - o2.getTimeOfCreation());
			}
		});
		*/
		return ops;
	}

}

