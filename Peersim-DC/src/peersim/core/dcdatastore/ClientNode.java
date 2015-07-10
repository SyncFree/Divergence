package peersim.core.dcdatastore;

import peersim.core.Node;

public interface ClientNode extends Node {
	
	/**
	 * Prefix of the parameters that defines protocols.
	 * @config
	 */
	public static final String PAR_PROT = "client.protocol";

	public void setUpLinkServers(ServerNode[] servers);
	
	public int getNumberServer();
	
	public ServerNode getServer(int index);
	
}
