package peersim.core.dcdatastore.clientEventGenerators;

import peersim.config.Configuration;

public abstract class BaseClientOperationGenerator implements ClientOperationGenerator {

	public static short clientProtocolID;
	public static String PAR_CLIENT_LAYER = "simulation.clientlayer";
	
	public BaseClientOperationGenerator() {
		BaseClientOperationGenerator.clientProtocolID = (short) Configuration.lookupPid(Configuration.getString(PAR_CLIENT_LAYER));
	}
	
}
