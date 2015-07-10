package peersim.core.dcdatastore.clientEventGenerators;

public class BaseReadReply<T> extends BaseReply<T> {

	public BaseReadReply(ClientReadOperation req, T reply, long timestamp) {
		super(req, reply, timestamp);
	}

}
