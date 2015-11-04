package divergence.clocks;

import divergence.clocks.CausalityClock;
import divergence.clocks.EventClock;
import divergence.clocks.VersionVector;

public class LocalClock {

    protected static final String DEFAULTSITEID = "0";
    protected VersionVector vv;
    protected String siteId;

    public LocalClock(String siteID) {
        vv = new VersionVector();
        this.siteId = siteID;
    }

    public EventClock nextEventClock() {
        return vv.recordNext(siteId);
    }

    public CausalityClock getCausalityClock() {
        return vv;
    }

    public String siteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
