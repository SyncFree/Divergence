package peersim.core.dcdatastore;

import java.util.HashMap;

import peersim.config.Configuration;
import peersim.core.Network;
import peersim.core.Node;

public class GeoReplicatedDatastoreNetwork extends Network {

	public static final String PAR_NUMBER_DCS = "network.numberDCs";
	
	public static final String PAR_SERVERS_PER_DC = "network.serversPerDC";
	
	public static final String PAR_CLIENTS_PER_DC = "network.clientsPerDC";
	
	public static final String PAR_SERVER_NODE = "network.serverNode";
	
	static protected short numberDCs;
	static protected int serversPerDC;
	static protected int clientsPerDC;
	
	static protected HashMap<Integer,ServerNode[]> servers;
	static protected HashMap<Integer,ClientNode[]> clients;
	
	static protected ClientNode[] allClients;
	
	static public ServerNode serverPrototype;
	static public ClientNode clientPrototype;
	
	protected GeoReplicatedDatastoreNetwork() {
		super();
	}
	
	/**
	* Reads configuration parameters, constructs the prototype node, and
	* populates the network by cloning the prototype.
	*/
	public static void reset() {
		
		if( prototype != null )
		{
			// not first experiment
			while( len>0 ) remove(); // this is to call onKill on all nodes
			prototype = null;
			node = null;
			servers = null;
			clients = null;
			serverPrototype = null;
			clientPrototype = null;
		}
		
		numberDCs = (short) Configuration.getInt(PAR_NUMBER_DCS);
		serversPerDC = Configuration.getInt(PAR_SERVERS_PER_DC,1);
		clientsPerDC = Configuration.getInt(PAR_CLIENTS_PER_DC);
		
		len = (serversPerDC * numberDCs) + (clientsPerDC * numberDCs);
		
		node = new Node[len];
		servers = new HashMap<Integer, ServerNode[]>();
		clients = new HashMap<Integer, ClientNode[]>();
		allClients = new ClientNode[numberDCs * clientsPerDC];
		for(int i = 0; i < numberDCs; i++) {
			servers.put(new Integer(i+1), new ServerNode[serversPerDC]);
			clients.put(new Integer(i+1), new ClientNode[clientsPerDC]);
		}
		
		// creating prototype node
		ServerNode serverTmp = null;
		ClientNode clientTmp = null;
		if (!Configuration.contains(PAR_NODE))
		{
			System.err.println(
			"Network: no client node defined, using GeneralClientNode");
			clientTmp = new GeneralClientNode(Configuration.PAR_CLIENT_PROT);
			System.err.println("GenerealClientNode loaded.");
		}
		else
		{
			clientTmp = (ClientNode) Configuration.getInstance(PAR_NODE);
		}
		
		if (!Configuration.contains(PAR_SERVER_NODE)) {
			System.err.println(
			"Network: no server node defined, using GeneralServerNode");
			serverTmp = new  GeneralServerNode(Configuration.PAR_SERVER_PROT);
		}
		else
		{
			serverTmp = (ServerNode) Configuration.getInstance(PAR_SERVER_NODE);
		}
		
		clientPrototype = clientTmp;
		prototype = clientPrototype;
		prototype.setIndex(-1);
		serverPrototype = serverTmp;
		serverPrototype.setIndex(-1);
		
		DCCommonState.setGlobalServerInstance((ServerNode)serverPrototype.clone());

		int totalCounter = 0;
		for(short i = 0; i < numberDCs; i++) {
			ServerNode[] tmp = servers.get(new Integer(i+1));
			for(int j = 0; j < serversPerDC; j++) {
				tmp[j] = (ServerNode) serverPrototype.clone();
				tmp[j].setDC(((short)(i+1)));
				node[totalCounter] = tmp[j];
				tmp[j].setIndex(totalCounter);
				totalCounter++;
			}
		}
		short c = 0;
		for(short i = 0; i < numberDCs; i++) {
			ClientNode[] tmp = clients.get(new Integer(i+1));
			for (int j = 0; j < clientsPerDC; j++) {
				tmp[j] = (ClientNode) clientPrototype.clone();
				tmp[j].setUpLinkServers(servers.get(new Integer(i+1)));
				node[totalCounter] = tmp[j];
				tmp[j].setIndex(totalCounter);
				totalCounter++;
				allClients[c] = tmp[j];
				c++;
			}
		}
		
		
		// cloning the nodes
		if(len > 0 )
		{
			for(int i=0; i<len; ++i)
			{
				node[i] = (Node)prototype.clone();
				node[i].setIndex(i);
			}
		}
		
		// Interface with super class.
		setCapacity(len);
		for(Node[] set: servers.values()) {
			for(int i = 0; i < set.length; i++)
				add(set[i]);
		}
		for(Node[] set: clients.values()) {
			for(int i = 0; i < set.length; i++)
				add(set[i]);
		}
	}
	
	public static int sizeServers() { return numberDCs * serversPerDC; }
	
	public static int sizeClients() { return numberDCs * clientsPerDC; }
	
	public static int numberDCs() { return numberDCs; }
	
	public static ServerNode[] getServersForDC(int dc) {
		return servers.get(new Integer(dc));
	}
	
	public static ClientNode getClient(int dc, int index) {
		return clients.get(dc)[index];
	}
	
	public static ClientNode getClient(int index) {
		return allClients[index];
	}
}
