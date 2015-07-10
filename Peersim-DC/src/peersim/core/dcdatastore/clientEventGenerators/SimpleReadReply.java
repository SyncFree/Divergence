package peersim.core.dcdatastore.clientEventGenerators;

public class SimpleReadReply extends BaseReadReply<Boolean> {

	public SimpleReadReply(ClientReadOperation req, Boolean reply,
			long timestamp) {
		super(req, reply, timestamp);
	}

}
