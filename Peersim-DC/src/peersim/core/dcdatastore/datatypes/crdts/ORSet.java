package peersim.core.dcdatastore.datatypes.crdts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.primitives.UnsignedInteger;

import peersim.core.dcdatastore.datatypes.DataObject;
import peersim.core.dcdatastore.datatypes.clocks.CausalityClock;
import peersim.core.dcdatastore.datatypes.clocks.LocalClock;
import peersim.core.dcdatastore.datatypes.clocks.Timestamp;
import peersim.core.dcdatastore.datatypes.crdts.interfaces.CvRDT;
import peersim.core.dcdatastore.datatypes.crdts.interfaces.ICRDTSet;
import peersim.core.dcdatastore.datatypes.exceptions.IncompatibleTypeException;
import peersim.core.dcdatastore.datatypes.util.Distances;
/**
 * CRDT OR-set with tombstones.
 * 
 * @author nmp
 * 
 * @param <V>
 */
public class ORSet<V> implements CvRDT, ICRDTSet<V>, DataObject<V, LocalClock>, Cloneable {
    private static final long serialVersionUID = 1L;
    private Map<V, Set<Timestamp>> elems;
    private Set<Timestamp> tomb; // tombstones
    private int size;
    
    public ORSet() {
        elems = new HashMap<V, Set<Timestamp>>();
        tomb = new HashSet<Timestamp>();
    }

    public ORSet(ORSet<V> other) {
        elems = new HashMap<V, Set<Timestamp>>(other.elems);
        tomb = new HashSet<Timestamp>(other.tomb);
    }
    
    public int getSize(){
    	return size;
    }
    
    /**
     * Returns true if e is in the set
     * 
     * @param o
     * @return
     */
    public synchronized boolean contains(Object o) {

        return elems.containsKey(o);
    }

//    /**
//     * Returns the set of elements in the OR-set
//     * 
//     * @return
//     */
//    @Override
//    public synchronized Set<V> getValue() {
//        return elems.keySet();
//    }

    /**
     * Insert element V in the set, using the given unique identifier.
     * 
     * @param e
     */
    public synchronized boolean add(V e, LocalClock clk) {
        Set<Timestamp> s = elems.get(e);
        if (s == null) {
            s = new HashSet<Timestamp>();
            elems.put(e, s);
        }
        return (s.add((Timestamp) clk.nextEventClock()));
    }

    /**
     * Delete element e from the set, using the given Timestamp.
     * 
     * @param e
     */
    public synchronized boolean remove(V e, LocalClock clk) {
        Set<Timestamp> s = elems.get(e);
        if (s == null) {
            return(false);
        }
        tomb.addAll(s);
        return(elems.remove(e) == null);
    }
    

    public synchronized void merge(CvRDT oo, CausalityClock thisClock,
            CausalityClock ooClock) throws IncompatibleTypeException {
        if (!(oo instanceof ORSet)) {
            throw new IncompatibleTypeException();
        }
        
		@SuppressWarnings("unchecked")//checked above
		ORSet<V> o = (ORSet<V>) oo;
        tomb.addAll(o.tomb);
        Iterator<Entry<V, Set<Timestamp>>> it = o.elems.entrySet().iterator();
        while (it.hasNext()) {
            Entry<V, Set<Timestamp>> e = it.next();
            Set<Timestamp> s = elems.get(e.getKey());
            if (s == null) {
                elems.put(e.getKey(), e.getValue());
            } else {
                s.addAll(e.getValue());
            }
        }
        it = elems.entrySet().iterator();
        while (it.hasNext()) {
            Entry<V, Set<Timestamp>> e = it.next();
            Set<Timestamp> s = e.getValue();
            s.removeAll(tomb);
            if (s.size() == 0) {
                it.remove();
            }
        }
    }

    public boolean equals(CvRDT o) {
        if (!(o instanceof ORSet<?>)) {
            return false;
        }
        ORSet<?> oi = (ORSet<?>) o;
        return oi.elems.equals(elems) && oi.tomb.equals(tomb);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{");
        Iterator<Entry<V, Set<Timestamp>>> it = elems.entrySet().iterator();
        while (it.hasNext()) {
            Entry<V, Set<Timestamp>> e = it.next();
            buf.append(e.getKey());
            buf.append("->[");
            Iterator<Timestamp> itE = e.getValue().iterator();
            while (itE.hasNext()) {
                Timestamp ev = itE.next();
                buf.append(ev);
                if (itE.hasNext()) {
                    buf.append(",");
                }
            }
            buf.append("]");
            if (it.hasNext()) {
                buf.append(",");
            }
        }
        buf.append("}");
        return buf.toString();
    }
    /**
     * Returns the distance between this ORSet and another ORSet by computing 
     * the symmetric distance of elements set alone, and adding it to 
     * the symmetric difference of the tomb-stones set.
     * NOTE: This does not rely on the read view by the client but on the operations
     * executed by an ORSet so far.
     * @param other
     * @return Symmetric distance between two ORSets. 
     */
    public double distanceExact(ORSet<V> other){
    	double elemsDistance=Distances.distance(this.elems.entrySet(), other.elems.entrySet());
    	double tombDistance=Distances.distance(this.tomb, other.tomb);
    	return elemsDistance+tombDistance;
    }
    
    /**
     * Returns the apparent distance (as seen by the client) between this ORSet and 
     * another ORSet by computing the symmetric distance of elements set.
     * @param other
     * @return Symmetric distance between two ORSets. 
     */
    public double distance(ORSet<V> other){
    	double elemsDistance=Distances.distance(this.elems.entrySet(), other.elems.entrySet());
    	return elemsDistance;
    }

    //TODO: Revise this method.
	public V getData() {
		return null;
	}

	//TODO: Revise this method.
	public void setData(V data) {
		;
	}

	//TODO: Revise this method.
	public void setData(V data, LocalClock metadata) {
		;
	}

	//TODO: Revise this method.
	public LocalClock getMetadata() {
		return new LocalClock(null);
	}


	@SuppressWarnings("unchecked")
	public double computeDivergence(DataObject<?, ?> dataObject) {
		return this.distance((ORSet<V>) dataObject);
	}
	
	//Override clone
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ORSet<V> nors = null;
		try {
			nors = (ORSet<V>) super.clone();
			nors.elems = new HashMap<V, Set<Timestamp>>();
			nors.tomb = new HashSet<Timestamp>(this.tomb);
			for(V k: this.elems.keySet())
				nors.elems.put(k, new HashSet<Timestamp>(this.elems.get(k)));
		} catch (CloneNotSupportedException e) {
			//should never happen
		}
		return nors;
	}

	public Iterator<V> iterator() {
		return elems.keySet().iterator();
	}
}
