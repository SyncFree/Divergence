package peersim.core.dcdatastore.observers.dbstate;

import java.util.Map;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.datatypes.DataObject;

public interface DatabaseObservable {

	public Map<String,DataObject<?,?>> getDatabaseState(ServerNode node);
	
}
