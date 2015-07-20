package peersim.core.dcdatastore.initializers.databaseinit;

import java.util.Map;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Node;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;
import peersim.core.dcdatastore.util.DataObject;

public abstract class DatabaseInitiator implements Control {

	private static final String PAR_PROTOCOL = "protocol";
	
	private static short protocolID;

	public DatabaseInitiator(String prefix) {
		DatabaseInitiator.protocolID = (short) Configuration.lookupPid(Configuration.getString(prefix + "." + PAR_PROTOCOL));
	}

	public boolean execute() {
		Map<String,DataObject<?,?>> objects = this.getDataToLoad();
		for(int i = 1 ; i <= GeoReplicatedDatastoreNetwork.numberDCs(); i++) {
			Node[] servers = GeoReplicatedDatastoreNetwork.getServersForDC(i);
			for(int j = 0; j < servers.length; j++) {
				DatabaseInitializable proto = (DatabaseInitializable) servers[j].getProtocol(protocolID);
				for(String key: objects.keySet()) {
					proto.storeObject(key, objects.get(key));
				}
			}
		}
		return false;
	}
	
	protected abstract Map<String,DataObject<?,?>> getDataToLoad();

}
