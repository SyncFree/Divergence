package peersim.core.dcdatastore.initializers.databaseinit;

import peersim.core.dcdatastore.util.DataObject;

public interface DatabaseInitializable {

	public void storeObject(String key, DataObject<?,?> object);
	
}
