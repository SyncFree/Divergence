package peersim.example.replicationsProtocols.data;

import peersim.core.dcdatastore.util.DataObject;

public class Counter implements DataObject<Integer, Integer> {

	private int data;
	private int ops;
	
	public Counter() {
		data = 0;
		ops = 0;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public void setData(Integer data, Integer metadata) {
		this.data = data;
		this.ops = metadata;
	}

	public Integer getMetadata() {
		return this.ops;
	}
	
	public void increment(int delta) {
		this.data+=delta;
		this.ops++;
	}

	public double computeDivergence(DataObject<?, ?> other) {
		return Math.abs(this.ops - (Integer) other.getMetadata());
	}

}
