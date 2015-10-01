package peersim.core.dcdatastore;

import java.util.Map;

import peersim.core.Node;
import peersim.core.dcdatastore.util.DataObject;

public interface ServerNode extends Node {

	/**
	 * Prefix of the parameters that defines protocols.
	 * @config
	 */
	public static final String PAR_PROT = "server.protocol";
	
	public void setDC(short dcID);
	
	public short getDC();
	
	public DataObject<?,?> write(String key, DataObject<?,?> o);
	
	public DataObject<?,?> read(String key);

	public Map<String, DataObject<?, ?>> getDatabaseCopy();
	
}
