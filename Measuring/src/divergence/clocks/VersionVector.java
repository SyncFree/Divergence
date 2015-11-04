package divergence.clocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import divergence.exceptions.IncompatibleTypeException;
import divergence.exceptions.InvalidParameterException;

/**
 * Class to represent common version vectors.
 * 
 * @author nmp
 */
public class VersionVector implements CausalityClock {

    private static final long serialVersionUID = 1L;
    protected Map<String, Long> vv;

    public VersionVector() {
        vv = new HashMap<String, Long>();
    }

    public VersionVector(VersionVector v) {
        vv = new HashMap<String, Long>(v.vv);
    }

    /**
     * Records the next event. <br>
     * IMPORTANT NOTE: Assumes no holes in event history.
     * 
     * @param siteid
     *            Site identifier.
     * @return Returns an event clock.
     * @throws IncompatibleTypeException
     */
    public EventClock recordNext(String siteid) {
        Long i = vv.get(siteid);
        if (i == null) {
            i = new Long(0);
        }
        Timestamp ts = new Timestamp(siteid, ++i);
        vv.put(ts.siteid, ts.counter);
        return ts;
    }

    /**
     * Records the next event. <br>
     * IMPORTANT NOTE: Assumes no holes in event history.
     * 
     * @param ec
     *            Event clock.
     * @return Returns true if the given event clock is included in this
     *         causality clock.
     * @throws IncompatibleTypeException
     */
    public void record(EventClock c) throws IncompatibleTypeException,
            InvalidParameterException {
        if (!(c instanceof Timestamp)) {
            throw new IncompatibleTypeException();
        }
        Timestamp cc = (Timestamp) c;
        Long i = vv.get(cc.siteid);
        if (i == null || i < cc.counter) {
            vv.put(cc.siteid, cc.counter);
        } else {
            throw new InvalidParameterException();
        }
    }

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
    @Override
    public int compareTo(CausalityClock c) throws IncompatibleTypeException {
        if (!(c instanceof VersionVector)) {
            throw new IncompatibleTypeException();
        }
        VersionVector cc = (VersionVector) c;
        boolean lessThan = false; // this less than c
        boolean greaterThan = false;
        Iterator<Entry<String, Long>> it = cc.vv.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Long> e = it.next();
            Long i = vv.get(e.getKey());
            if (i == null) {
                lessThan = true;
                if (greaterThan) {
                    return CausalityClock.CMP_CONCURRENT;
                }
            } else {
                long iOther = e.getValue();
                long iThis = i;
                if (iThis < iOther) {
                    lessThan = true;
                    if (greaterThan) {
                        return CausalityClock.CMP_CONCURRENT;
                    }
                } else if (iThis > iOther) {
                    greaterThan = true;
                    if (lessThan) {
                        return CausalityClock.CMP_CONCURRENT;
                    }
                }
            }
        }
        it = vv.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Long> e = it.next();
            Long i = cc.vv.get(e.getKey());
            if (i == null) {
                greaterThan = true;
                if (lessThan) {
                    return CausalityClock.CMP_CONCURRENT;
                }
            } else {
                long iThis = e.getValue();
                long iOther = i;
                if (iThis < iOther) {
                    lessThan = true;
                    if (greaterThan) {
                        return CausalityClock.CMP_CONCURRENT;
                    }
                } else if (iThis > iOther) {
                    greaterThan = true;
                    if (lessThan) {
                        return CausalityClock.CMP_CONCURRENT;
                    }
                }
            }
        }
        if (greaterThan && lessThan) {
            return CausalityClock.CMP_CONCURRENT;
        }
        if (greaterThan) {
            return CausalityClock.CMP_DOMINATES;
        }
        if (lessThan) {
            return CausalityClock.CMP_ISDOMINATED;
        }
        return CausalityClock.CMP_EQUALS;
    }

    /**
     * Checks if a given event clock is reflected in this clock
     * 
     * @param c
     *            Event clock.
     * @return Returns true if the given event clock is included in this
     *         causality clock.
     * @throws IncompatibleTypeException
     */
    @Override
    public boolean includes(EventClock c) throws IncompatibleTypeException {
        if (!(c instanceof Timestamp)) {
            throw new IncompatibleTypeException();
        }
        Timestamp cc = (Timestamp) c;
        Long i = vv.get(cc.siteid);
        return i != null && cc.counter <= i;
    }

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
    public int merge(CausalityClock c) throws IncompatibleTypeException {
        if (!(c instanceof VersionVector)) {
            throw new IncompatibleTypeException();
        }
        VersionVector cc = (VersionVector) c;
        boolean lessThan = false; // this less than c
        boolean greaterThan = false;
        Iterator<Entry<String, Long>> it = cc.vv.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Long> e = it.next();
            Long i = vv.get(e.getKey());
            if (i == null) {
                lessThan = true;
                vv.put(e.getKey(), e.getValue());
            } else {
                long iOther = e.getValue();
                long iThis = i;
                if (iThis < iOther) {
                    lessThan = true;
                    vv.put(e.getKey(), iOther);
                } else if (iThis > iOther) {
                    greaterThan = true;
                }
            }
        }
        it = vv.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Long> e = it.next();
            Long i = cc.vv.get(e.getKey());
            if (i == null) {
                greaterThan = true;
            } else {
                long iThis = e.getValue();
                long iOther = i;
                if (iThis < iOther) {
                    lessThan = true;
                    vv.put(e.getKey(), iOther);
                } else if (iThis > iOther) {
                    greaterThan = true;
                }
            }
        }
        if (greaterThan && lessThan) {
            return CausalityClock.CMP_CONCURRENT;
        }
        if (greaterThan) {
            return CausalityClock.CMP_DOMINATES;
        }
        if (lessThan) {
            return CausalityClock.CMP_ISDOMINATED;
        }
        return CausalityClock.CMP_EQUALS;

    }

    /**
     * Returns the number of operations reflected in one vector that are not
     * reflected in the other.
     * 
     * @param c
     *            Clock to comapre to
     * @return
     * @throws IncompatibleTypeException
     *             Case comparison cannot be made
     */
    public long difference(CausalityClock c) throws IncompatibleTypeException {
        if (!(c instanceof VersionVector)) {
            throw new IncompatibleTypeException();
        }
        VersionVector cc = (VersionVector) c;
        Set<String> s = new HashSet<String>();
        s.addAll(cc.vv.keySet());
        s.addAll(vv.keySet());
        long dif = 0;
        Iterator<String> it = s.iterator();
        while (it.hasNext()) {
            String k = it.next();
            Long localV = vv.get(k);
            Long remV = cc.vv.get(k);
            dif = dif
                    + Math.abs((localV == null ? 0 : localV)
                            - (remV == null ? 0 : remV));
        }
        return dif;
    }

    /**
     * Create a copy of this causality clock.
     */
    public CausalityClock clone() {
        return new VersionVector(this);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        Iterator<Entry<String, Long>> it = vv.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Long> e = it.next();
            buf.append(e.getKey() + ":" + e.getValue());
            if (it.hasNext()) {
                buf.append(",");
            }
        }
        buf.append("]");
        return buf.toString();
    }

}
