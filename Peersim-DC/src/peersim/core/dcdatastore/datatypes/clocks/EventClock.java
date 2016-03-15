package peersim.core.dcdatastore.datatypes.clocks;

import java.io.Serializable;

public interface EventClock extends Serializable, Comparable<EventClock> {
    /**
     * Create a copy of this event clock.
     */
    public EventClock clone();

    public String getIdentifier();

}
