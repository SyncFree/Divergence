package peersim.core.dcdatastore.clientEventGenerators;

public class BaseWriteReply<T> extends BaseReply<T> {

	public BaseWriteReply(ClientReadOperation req, T reply, long timestamp) {
		super(req, reply, timestamp);
	}

}
