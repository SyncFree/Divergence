package peersim.core.dcdatastore.datatypes.crdts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import peersim.core.dcdatastore.datatypes.DataObject;
import peersim.core.dcdatastore.datatypes.clocks.CausalityClock;
import peersim.core.dcdatastore.datatypes.clocks.LocalClock;
import peersim.core.dcdatastore.datatypes.crdts.interfaces.CvRDT;
import peersim.core.dcdatastore.datatypes.exceptions.IncompatibleTypeException;
import peersim.core.dcdatastore.datatypes.util.Distances;

public class Counter implements CvRDT, DataObject<Integer, LocalClock>, Cloneable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> adds;
    private Map<String, Integer> rems;
    private int val;

    public Counter(int initial) {
        this.val = initial;
        this.adds = new HashMap<String, Integer>();
        this.rems = new HashMap<String, Integer>();
    }

    public Counter() {
        this(0);
    }

    public int value() {
        return this.val;
    }

    public int add(int n, LocalClock clock) {
        return add(n, clock.siteId());
    }

    // public int add(int n, EventClock ec) {
    // String siteId = ec.getIdentifier();
    // return add(n, siteId);
    // }

    private int add(int n, String siteId) {
        if (n < 0) {
            return sub(-n, siteId);
        }

        int v;
        if (this.adds.containsKey(siteId)) {
            v = this.adds.get(siteId) + n;
        } else {
            v = n;
        }

        this.adds.put(siteId, v);
        this.val += n;
        return this.val;
    }

    public int sub(int n, LocalClock clock) {
        return sub(n, clock.siteId());
    }

    // public int sub(int n, EventClock ec) {
    // return sub(n, ec.getIdentifier());
    // }

    private int sub(int n, String siteId) {
        if (n < 0) {
            return add(-n, siteId);
        }
        int v;
        if (this.rems.containsKey(siteId)) {
            v = this.rems.get(siteId) + n;
        } else {
            v = n;
        }

        this.rems.put(siteId, v);
        this.val -= n;
        return this.val;
    }

    @Override
    public void merge(CvRDT other, CausalityClock thisClock,
            CausalityClock thatClock) throws IncompatibleTypeException {
        if (!(other instanceof Counter)) {
            throw new IncompatibleTypeException();
        }

        Counter that = (Counter) other;
        for (Entry<String, Integer> e : that.adds.entrySet()) {
            if (!this.adds.containsKey(e.getKey())) {
                int v = e.getValue();
                this.val += v;
                this.adds.put(e.getKey(), v);
            } else {
                int v = this.adds.get(e.getKey());
                if (v < e.getValue()) {
                    this.val = this.val - v + e.getValue();
                    this.adds.put(e.getKey(), e.getValue());
                }
            }
        }

        for (Entry<String, Integer> e : that.rems.entrySet()) {
            if (!this.rems.containsKey(e.getKey())) {
                int v = e.getValue();
                this.val -= v;
                this.rems.put(e.getKey(), v);
            } else {
                int v = this.rems.get(e.getKey());
                if (v < e.getValue()) {
                    this.val = this.val + v - e.getValue();
                    this.rems.put(e.getKey(), e.getValue());
                }
            }
        }
    }

    @Override
    public boolean equals(CvRDT other) {
        if (!(other instanceof Counter)) {
            return false;
        }
        Counter that = (Counter) other;
        return that.val == this.val && that.adds.equals(this.adds)
                && that.rems.equals(this.rems);
    }

    @Override
    public String toString() {
        return "" + val;
    }
    
    /**
     * Distance between this and other Counter.
     * @param other
     * @return distance (can be -ve)
     */
	public double distance(Counter other){
		return Distances.distance(this.val, other.val);
		
	}

    /**
     * Absolute distance between this and other Counter.
     * @param other
     * @return distance (always +ve)
     */
	public double absDistance(Counter other){
		return Distances.absDistance(this.val, other.val);	
	}

	@Override
	public Integer getData() {
		return new Integer(this.value());
	}

	@Override
	public void setData(Integer data) {
		this.val = data;
	}

	@Override
	public void setData(Integer data, LocalClock metadata) {
		if(data >= val)
			this.add(data - val, metadata);
		else
			this.sub(val - data, metadata);
	}

	// TODO: Revise this method.
	@Override
	public LocalClock getMetadata() {
		return new LocalClock(null);
	}

	@Override
	public double computeDivergence(DataObject<?, ?> dataObject) {
		return this.distance((Counter) dataObject);
	}
	
    // TODO Re-implement the hashCode() method!!!

	
	// Support for clone
	@Override
	public Object clone() {
		Counter nc = null;
		try {
			nc = (Counter) super.clone(); 
			nc.adds = new HashMap<String, Integer>();
			for(String k: this.adds.keySet())
				nc.adds.put(k, new Integer(this.adds.get(k)));
			nc.rems = new HashMap<String, Integer>();
			for(String k: this.rems.keySet())
				nc.rems.put(k, new Integer(this.rems.get(k)));
			nc.val = this.val;
		} catch (CloneNotSupportedException e) {
			//should never happen
		}
		
		return nc;
	}
}
