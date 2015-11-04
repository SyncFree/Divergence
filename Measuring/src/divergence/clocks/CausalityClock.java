package divergence.clocks;

import java.io.Serializable;

import divergence.exceptions.IncompatibleTypeException;
import divergence.exceptions.InvalidParameterException;

/**
 * Base class for clocks that allow to trace causality, such as version vector
 * and dotted version vectors
 * 
 * @author nmp
 */
public interface CausalityClock extends Serializable {
    // TODO Use enum?
    int CMP_EQUALS = 0;
    int CMP_DOMINATES = 1;
    int CMP_ISDOMINATED = 2;
    int CMP_CONCURRENT = 3;

    /**
     * Records the next event. <br>
     * IMPORTANT NOTE: Assumes no holes in event history.
     * 
     * @param siteid
     *            Site identifier.
     * @return Returns an event clock.
     * @throws IncompatibleTypeException
     */
    EventClock recordNext(String siteid);

    /**
     * Records the next event.
     * 
     * @param ec
     *            Event clock.
     * @return Returns true if the given event clock is included in this
     *         causality clock.
     * @throws IncompatibleTypeException
     */
    void record(EventClock ec) throws IncompatibleTypeException,
            InvalidParameterException;

    /**
     * Checks if a given event clock is reflected in this clock
     * 
     * @param c
     *            Event clock.
     * @return Returns true if the given event clock is included in this
     *         causality clock.
     * @throws IncompatibleTypeException
     */
    boolean includes(EventClock c) throws IncompatibleTypeException;

    /**
     * Compares two causality clock.
     * 
     * @param c
     *            Clock to comapre to
     * @return Returns one of the following:<br>
     *         CMP_EQUALS : if clocks are equal; <br>
     *         CMP_DOMINATES : if this clock dominates the given c clock; <br>
     *         CMP_ISDOMINATED : if this clock is dominated by the given c
     *         clock; <br>
     *         CMP_CONCUREENT : if this clock and the given c clock are
     *         concurrent; <br>
     * @throws IncompatibleTypeException
     *             Case comparison cannot be made
     */
    int compareTo(CausalityClock c) throws IncompatibleTypeException;

    /**
     * Merge this clock with the given c clock.
     * 
     * @param c
     *            Clock to merge to
     * @return Returns one of the following, based on the initial value of
     *         clocks:<br>
     *         CMP_EQUALS : if clocks were equal; <br>
     *         CMP_DOMINATES : if this clock dominated the given c clock; <br>
     *         CMP_ISDOMINATED : if this clock was dominated by the given c
     *         clock; <br>
     *         CMP_CONCUREENT : if this clock and the given c clock were
     *         concurrent; <br>
     * @throws IncompatibleTypeException
     *             Case comparison cannot be made
     */
    int merge(CausalityClock c) throws IncompatibleTypeException;

    /**
     * Create a copy of this causality clock.
     */
    CausalityClock clone();

    /**
     * Delivers an iterator of the most recent events for each site.
     * 
     * @return Returns the iterator containing the EventClocks for each site.
     * @throws IncompatibleTypeException
     */

}
