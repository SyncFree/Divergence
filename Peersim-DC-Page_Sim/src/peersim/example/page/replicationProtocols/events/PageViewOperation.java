package peersim.example.page.replicationProtocols.events;

import peersim.core.dcdatastore.ClientNode;

import peersim.core.dcdatastore.ServerNode;
import peersim.core.dcdatastore.clientEventGenerators.ClientWriteOperation;

public class PageViewOperation extends ClientWriteOperation<Integer> {
	private int pageId;
	
	public PageViewOperation(ClientNode c, long time, int pageId) {
		super((short) 74, c, time);
		this.pageId = pageId;
	}
	
	public PageViewOperation(ClientNode c, ServerNode d, long time, int pageId) {
		super((short) 74, c, d, time);
		this.pageId = pageId;
	}

	public int getPageId(){		
		return this.pageId;
	}


}
