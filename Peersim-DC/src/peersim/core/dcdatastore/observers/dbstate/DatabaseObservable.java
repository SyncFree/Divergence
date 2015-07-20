package peersim.core.dcdatastore.observers.dbstate;

import java.util.Map;

import peersim.core.dcdatastore.util.DataObject;

public interface DatabaseObservable {

	public Map<String,DataObject<?,?>> getDatabaseState();
	
}
