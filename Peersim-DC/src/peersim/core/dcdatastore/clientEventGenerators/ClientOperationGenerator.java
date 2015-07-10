package peersim.core.dcdatastore.clientEventGenerators;

import java.util.List;

public interface ClientOperationGenerator {

	public void init(String filename);
	
	public ClientOperationGenerationEvent hasMoreOperations();

	public List<ClientOperation> getNextSetOfOperations();
	
}
