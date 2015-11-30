package peersim.example.replicationProtocols.init;

import java.util.HashMap;
import java.util.Map;

import peersim.core.dcdatastore.clientEventGenerators.BaseClientOperationGenerator;
import peersim.core.dcdatastore.datatypes.DataObject;
import peersim.core.dcdatastore.initializers.databaseinit.DatabaseInitiator;
import peersim.example.replicationProtocols.clients.RandomCounterOperationGenerator;
import peersim.example.replicationsProtocols.data.Counter;

public class CounterLoader extends DatabaseInitiator {

	public CounterLoader(String prefix) {
		super(prefix);
	}

	@Override
	protected Map<String, DataObject<?, ?>> getDataToLoad() {
		String[] keys = ((RandomCounterOperationGenerator)BaseClientOperationGenerator.instance).getObjectIdentifiers();
		
		Map<String, DataObject<?, ?>> objects = new HashMap<String, DataObject<?, ?>>();
		
		for(int i = 0; i < keys.length; i++)
			objects.put(keys[i], new Counter());
		
		return objects;
	}

}
