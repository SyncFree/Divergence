package peersim.core.dcdatastore.clientEventGenerators;

public class ClientOperationGenerationEvent {

	private long time;
	
	public ClientOperationGenerationEvent(long time) {
		this.time = time;
	}

	public long getTime() {
		return this.time;
	}
}
