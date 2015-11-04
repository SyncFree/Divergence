package divergence.dataTypes.crdts.interfaces;

import divergence.clocks.LocalClock;

public interface ICRDTSet<V> extends CvRDT{
    /**
     * Returns true if e is in the set
     * 
     * @param e
     * @return
     */
    boolean contains(V e);

//    /**
//     * Returns the set of elements in the OR-set
//     * 
//     * @return
//     */
//    Set<V> getValue();

    /**
     * Insert element V in the set, using the given unique identifier.
     * 
     * @param e
     */
    void add(V e, LocalClock clk);

    /**
     * Delete element e from the set, using the given timestamp.
     * 
     * @param e
     */
    void remove(V e, LocalClock clk);

}
