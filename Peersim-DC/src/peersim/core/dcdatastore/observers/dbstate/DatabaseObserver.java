package peersim.core.dcdatastore.observers.dbstate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.datatypes.DataObject;

public class DatabaseObserver implements Control {

	public static final String PAR_PROTOCOL = "protocol";
	public static final String PAR_FILENAME = "filename";
	
	private static int protocolID;
	private PrintStream output;
	
	public DatabaseObserver(String prefix) {
		DatabaseObserver.protocolID = Configuration.lookupPid(Configuration.getString(prefix+"."+PAR_PROTOCOL));
		try {
			output = new PrintStream(new File(Configuration.getString(prefix+"."+PAR_FILENAME)));
		} catch (FileNotFoundException e) {
			System.err.println(this.getClass().getName() +": Error opening output file: '" + Configuration.getString(prefix+"."+PAR_FILENAME));
			output = System.out;
		}
	}

	public boolean execute() {
		this.output.println("Database State report: " + DCCommonState.getTime());
		for( int i = 1; i <= GeoReplicatedDatastoreNetwork.numberDCs(); i++ ) {
			ServerNode servers[] = GeoReplicatedDatastoreNetwork.getServersForDC(i);
			for (int j = 0; j < servers.length; j++) {
				this.output.println("Server " + j + " (DC: " + i + ")");
				Map<String,DataObject<?,?>> dbCopy = ((DatabaseObservable)servers[j].getProtocol(protocolID)).getDatabaseState((ServerNode) servers[j]);
				for(String k: dbCopy.keySet()) {
					this.output.println("Key " + k + " -> " + dbCopy.get(k).toString());
				}
			}
		}
		return false;
	}

}
