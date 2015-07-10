package peersim.core.dcdatastore.replicationProtocol.divergenceControl;

import java.util.ArrayList;
import java.util.Iterator;

public class DivergenceMetrics implements Cloneable {

	private ArrayList<Double> divergenceMeasures;
	private double min;
	private double max;
	
	public DivergenceMetrics() {
		this.divergenceMeasures = new ArrayList<Double>();
		this.min = 0;
		this.max = 0;
	}
	
	public Object clone() {
		DivergenceMetrics newDM = null;
		try {
			newDM = (DivergenceMetrics) super.clone();
			newDM.divergenceMeasures = new ArrayList<Double>();
			newDM.min = 0;
			newDM.max = 0;
		} catch (CloneNotSupportedException e) {
			//Never Happens
		} 
		return newDM;
	}
	
	public void reset() {
		this.divergenceMeasures.clear();
		this.min = 0;
		this.max = 0;
	}
	
	public double getMin() {
		return this.min;
	}
	
	public double getMax() {
		return this.max;
	}
	
	public double getAverage() {
		double total = 0;
		for(Double d: this.divergenceMeasures)
			total+= d;
		return total/this.divergenceMeasures.size();
	}

	public Iterator<Double> getMeasures() {
		return this.divergenceMeasures.iterator();
	}
	
	public int getMeasuresCount() {
		return this.divergenceMeasures.size();
	}
	
	public void addMeasure(double measure) {
		if(this.divergenceMeasures.size() == 0) {
			this.min = measure;
			this.max = measure;
		}
		this.divergenceMeasures.add(measure);
		if(this.min > measure)
			this.min = measure;
		if(this.max < measure)
			this.max = measure;
			
	}
}
