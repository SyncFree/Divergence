package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class PageUpdateOperation extends ClientWriteOperation<Integer> {
	private int pageId;
	
	public PageUpdateOperation(ClientNode c, long time, int pageId) {
		super((short) 75, c, time);
		this.pageId = pageId;
	}
	
	public PageUpdateOperation(ClientNode c, ServerNode d, long time, int pageId) {
		super((short) 75, c, d, time);
		this.pageId = pageId;
	}
	
	public int getId (){
		return pageId; 
	}

}
