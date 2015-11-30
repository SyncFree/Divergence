package peersim.core.dcdatastore.datatypes.crdts.interfaces;

import java.io.Serializable;

import peersim.core.dcdatastore.datatypes.clocks.CausalityClock;
import peersim.core.dcdatastore.datatypes.exceptions.IncompatibleTypeException;

public interface CvRDT extends Serializable {
    void merge(CvRDT other, CausalityClock thisClock, CausalityClock thatClock)
            throws IncompatibleTypeException;
    
    boolean equals(CvRDT o);
}
