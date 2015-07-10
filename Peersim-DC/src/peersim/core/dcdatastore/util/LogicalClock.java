package peersim.core.dcdatastore.util;

public interface LogicalClock extends Comparable<LogicalClock>{
	
	public void merge(LogicalClock lc);
	
	public void increment(int index);
	
	public void set(int index, long value);

}
