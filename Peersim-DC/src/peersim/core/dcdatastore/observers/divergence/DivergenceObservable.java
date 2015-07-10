package peersim.core.dcdatastore.observers.divergence;

import java.util.Iterator;

public interface DivergenceObservable {
	
	public double getMinDivergence();
	public double getMaxDivergence();
	public double getAverageDivergence();
	public Iterator<Double> getDivergenceMeasures();
	public int getDivergenceMeasuresCount();
	public void resetDivergenceMetrics();
	
}
