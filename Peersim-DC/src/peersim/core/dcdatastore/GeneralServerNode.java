package peersim.core.dcdatastore;

import java.util.HashMap;

import peersim.core.dcdatastore.util.DataObject;

public class GeneralServerNode extends GeneralDCNode implements ServerNode {

	private short dcID;
	private HashMap<String,DataObject<?,?>> db;
	
	public GeneralServerNode(String prefix) {
		super(prefix);
		this.dcID = 0;
		this.db = new HashMap<String,DataObject<?,?>>();
	}

	public void setDC(short dcID) {
		this.dcID = dcID;
	}

	public short getDC() {
		return this.dcID;
	}

	public DataObject<?,?> write(String key, DataObject<?,?> o) {
		return this.db.put(key, o);
	}

	public DataObject<?,?> read(String key) {
		return this.db.get(key);
	}

}
