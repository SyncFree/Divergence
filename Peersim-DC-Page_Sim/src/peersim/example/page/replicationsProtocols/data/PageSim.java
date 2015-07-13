package peersim.example.page.replicationsProtocols.data;

import peersim.core.dcdatastore.util.DataObject;

public class PageSim implements DataObject<Integer, Integer> {

	private int pageId; 	// is the first of DataObject Integers
	//private int courseId; 	// should we use it??
	private int value;		// is the metadata
	
	public PageSim(int id) {
		pageId = id;
		value = 0;
	}

	public Integer getData() {
		return pageId;
	}

	public void setData(Integer pageId) {
		this.pageId = pageId;
	}
	
	public void setData(Integer pageId, Integer value){
		this.pageId=pageId;
		this.value=value;
	}
	
	public Integer getValue() {
		return value;
	}


	
	public void incValue() {
		this.value += 1;
	}

	public Integer getMetadata() {
		return this.value;
	}
	
	public double computeDivergence(DataObject<?, ?> other) {
		return Math.abs(this.value - (Integer) other.getMetadata());
	}
}
