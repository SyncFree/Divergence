package peersim.example.page.replicationsProtocols.data;

import peersim.core.dcdatastore.util.DataObject;

public class ResourceSim implements DataObject<Integer, Integer> {

	private int resId; 	// is the first of DataObject Integers
	//private int courseId; 	// should we use it??
	private int value;		// is the metadata
	
	public ResourceSim(int id) {
		resId = id;
		value = 0;
	}

	public Integer getData() {
		return resId;
	}

	public void setData(Integer resId) {
		this.resId = resId;
	}
	
	public void setData(Integer resId, Integer value){
		this.resId=resId;
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
