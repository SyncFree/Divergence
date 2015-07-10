package peersim.core.dcdatastore.clientEventGenerators;

public class SimpleWriteReply extends BaseWriteReply<Boolean> {

	public SimpleWriteReply(ClientReadOperation req, Boolean reply,
			long timestamp) {
		super(req, reply, timestamp);
	}

}
