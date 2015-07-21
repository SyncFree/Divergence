package peersim.core.dcdatastore.initializers.databaseinit;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.util.DataObject;

public interface DatabaseInitializable {

	public void storeObject(ServerNode node, String key, DataObject<?,?> object);
	
}
