package divergence.clocks;

public class Timestamp implements EventClock {

    private static final long serialVersionUID = 1L;
    public String siteid;
    public long counter;

    public Timestamp() {

    }

    public Timestamp(String siteid, long counter) {
        this.siteid = siteid;
        this.counter = counter;
    }

    public int hashCode() {
        return siteid.hashCode() + (int) counter;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Timestamp)) {
            return false;
        }
        Timestamp ts = (Timestamp) obj;
        return ts.counter == counter && ts.siteid.equals(siteid);
    }

    public String toString() {
        return "(" + siteid + "," + counter + ")";
    }

    /**
     * Create a copy of this event clock.
     */
    public EventClock clone() {

        return new Timestamp(this.siteid, this.counter);
    }

    public int compareTo(EventClock ot) {
        Timestamp t = (Timestamp) ot;
        if (counter < t.counter) {
            return -1;
        } else if (counter > t.counter) {
            return 1;
        } else {
            return siteid.compareTo(t.siteid);
        }

    }

    public int size() {
        return 8 + siteid.length();
    }

    @Override
    public String getIdentifier() {
        return siteid;
    }

}
