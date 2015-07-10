package peersim.core.dcdatastore;

public class GeneralClientNode extends GeneralDCNode implements ClientNode, Cloneable {

	ServerNode[] servers;
	
	public GeneralClientNode(String prefix) {
		super(prefix);
		servers = null;
	}
	
	public GeneralClientNode clone() {
		GeneralClientNode gcn = null;
		gcn = (GeneralClientNode) super.clone();
		return gcn;
	}

	public void setUpLinkServers(ServerNode[] servers) {
		this.servers = servers;
	}

	public int getNumberServer() {
		return servers.length;
	}

	public ServerNode getServer(int index) {
		return servers[index];
	}


}
