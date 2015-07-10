package peersim.core.dcdatastore.controls;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Node;
import peersim.core.dcdatastore.GeoReplicatedDatastoreNetwork;

public class ServerInitiator implements Control {

	private static final String PAR_PROTOCOL = "protocol";
	
	private static short protocolID;

	public ServerInitiator(String prefix) {
		ServerInitiator.protocolID = (short) Configuration.lookupPid(Configuration.getString(prefix + "." + PAR_PROTOCOL));
	}

	public boolean execute() {
		for(int i = 1 ; i <= GeoReplicatedDatastoreNetwork.numberDCs(); i++) {
			Node[] servers = GeoReplicatedDatastoreNetwork.getServersForDC(i);
			for(int j = 0; j < servers.length; j++)
				((Initializable) servers[j].getProtocol(protocolID)).init(servers[j], protocolID);
		}
		return false;
	}

}
