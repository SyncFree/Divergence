package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class PageAddOperation extends ClientWriteOperation<Integer> {
	private int pageId;

	public PageAddOperation(ClientNode c, ServerNode d, long time, int pageId) {
		super((short) 73, c, d, time);
		this.pageId = pageId;
	}
	
	public PageAddOperation(ClientNode c, long time, int pageId) {
		super((short) 73, c, time);
		this.pageId = pageId;
	}
	
	public int getId (){
		return pageId; 
	}

}
