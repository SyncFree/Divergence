package peersim.example.replicationsProtocols.data;

import peersim.core.dcdatastore.DCCommonState;
import peersim.core.dcdatastore.datatypes.DataObject;

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
		if(other == null) { System.err.println("Master copy is null (Master id is: " + DCCommonState.globalServer().getID() + ")");}
		
		if(other instanceof Counter) {
			Counter c = (Counter) other;
			return (double) Math.abs(this.ops - c.getMetadata());
		} else {
			return (double) 0;
		}
	}
	
	public Object clone() {
		Counter c = null;
		try {
			c = (Counter) super.clone();
			c.data = data;
			c.ops = ops;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

}
