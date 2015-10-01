package peersim.core.dcdatastore.transport;

import java.io.File;
import java.util.Scanner;

import peersim.config.Configuration;
import peersim.core.Node;
import peersim.core.dcdatastore.ClientNode;
import peersim.core.dcdatastore.DCDataStoreSimulator;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.ServerNode;
import peersim.transport.Transport;

public class DCTransportLayer implements Transport, Cloneable {

	public static final String PAR_NUMBER_DCS = GeoReplicatedDatastoreNetwork.PAR_NUMBER_DCS;
	public static final String PAR_CONFIG_FILE = "config";
	
	protected int n_dcs;
	private int[][] interDCLatency;
	private int[] clientToDCLatency;
	private int[] DCToClientLatency;
	
	public DCTransportLayer(String prefix) {
		System.err.println("Transport layer [prefix: " + prefix + "]"); 
		System.err.println("Loading config: " + PAR_NUMBER_DCS);
		this.n_dcs = Configuration.getInt(PAR_NUMBER_DCS);
		this.interDCLatency = new int[n_dcs][n_dcs];
		this.clientToDCLatency = new int[n_dcs];
		this.DCToClientLatency = new int[n_dcs];
		Scanner configFileReader = null;
		try{
			System.err.println("Loading config: " + prefix + "." + PAR_CONFIG_FILE);
			configFileReader = new Scanner(new File(Configuration.getString(prefix + "." + PAR_CONFIG_FILE)));
			configFileReader.nextLine();
			for(int i = 0; i < n_dcs; i++) {
				String[] lats = configFileReader.nextLine().split(" ");
				for(int j = 0; j < n_dcs; j++)
					interDCLatency[i][j] = Integer.parseInt(lats[j]);
			}
			configFileReader.nextLine();
			String[] lats_c_d = configFileReader.nextLine().split(" ");
			configFileReader.nextLine();
			String[] lats_d_c = configFileReader.nextLine().split(" ");
			for(int i = 0; i < n_dcs; i++) {
				clientToDCLatency[i] = Integer.parseInt(lats_c_d[i]);
				DCToClientLatency[i] = Integer.parseInt(lats_d_c[i]);
			}		
		} catch (Exception e) {
			System.err.println("Error parsing network model file: '" +Configuration.getString(prefix + "." + PAR_CONFIG_FILE)+"'" );
			e.printStackTrace();
		} finally {
			if(configFileReader != null)
				configFileReader.close();
			configFileReader = null;
		}
	}

	public Object clone() {
		return this;
	}
	
	public void send(Node src, Node dest, Object msg, int pid) {
		long latency = getLatency(src, dest);
		//System.err.println("@" + DCCommonState.getTime() + "  Transport layer: Scheduling event at: " + (DCCommonState.getTime() + latency));
		DCDataStoreSimulator.add(latency, msg, dest, pid);
	}

	protected void send(Node src, Node dest, Object msg, int pid, long extraDelay) {
		long latency = getLatency(src, dest) + extraDelay;
		DCDataStoreSimulator.add(latency, msg, dest, pid);
	}
	
	public long getLatency(Node src, Node dest) {
		if(src instanceof ClientNode || dest instanceof ClientNode) {
			//Communication between client and server (or vice versa)
			if(src instanceof ClientNode) {
				//client to server communication
				//ClientNode dc_src = (ClientNode) src;
				ServerNode dc_dest = (ServerNode) dest;
				return clientToDCLatency[dc_dest.getDC()-1];
			} else {
				//server to client communication
				ServerNode dc_src = (ServerNode) src;
				//ClientNode dc_dest = (ClientNode) dest;
				return DCToClientLatency[dc_src.getDC()-1];
			}
		} else {
			//Communication between servers in different DCs
			ServerNode dc_src = (ServerNode) src;
			ServerNode dc_dest = (ServerNode) dest;
			return interDCLatency[dc_src.getDC()-1][dc_dest.getDC()-1];
		}
	}

}
