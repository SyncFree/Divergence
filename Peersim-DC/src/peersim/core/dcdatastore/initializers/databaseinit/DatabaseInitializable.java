package peersim.core.dcdatastore.initializers.databaseinit;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.datatypes.DataObject;

public interface DatabaseInitializable {

	public void storeObject(ServerNode node, String key, DataObject<?,?> object);
	
}
