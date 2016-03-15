package divergence.dataTypes.crdts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import divergence.clocks.*;
import divergence.dataTypes.crdts.interfaces.CvRDT;
import divergence.exceptions.IncompatibleTypeException;
import divergence.util.Distances;

public class Counter implements CvRDT {
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
	
    // TODO Re-implement the hashCode() method!!!

}
