package divergence.dataTypes.crdts.interfaces;

import java.io.Serializable;

import divergence.clocks.CausalityClock;
import divergence.exceptions.IncompatibleTypeException;

public interface CvRDT extends Serializable {
    void merge(CvRDT other, CausalityClock thisClock, CausalityClock thatClock)
            throws IncompatibleTypeException;
    
    boolean equals(CvRDT o);
}
