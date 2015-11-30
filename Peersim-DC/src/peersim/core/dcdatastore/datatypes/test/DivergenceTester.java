package peersim.core.dcdatastore.datatypes.test;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import peersim.core.dcdatastore.datatypes.clocks.LocalClock;
import peersim.core.dcdatastore.datatypes.crdts.Counter;
import peersim.core.dcdatastore.datatypes.crdts.ORSet;
import peersim.core.dcdatastore.datatypes.exceptions.IncompatibleTypeException;
import peersim.core.dcdatastore.datatypes.util.Distances;

/**
 * Testbed for Divergence.
 * 
 * @author Ali Shoker
 *
 */
public class DivergenceTester {


	public static void main(String[] args) {
		
		distanceIntTester();
		absDistanceTester();
		distanceCounterTester();
		absDistanceCounterTester();
		distanceSetTester();
		distanceORSetTester();
	}


	public static void distanceIntTester(){
		
		int val1=5, val2=10;
		display("Distance between integers:");
		display("-- val1=5, val2=10, distance="+Distances.distance(val1, val2));
	}
	
	public static void absDistanceTester(){
		
		int val1=5, val2=10;
		display("Absolute distance between integers:");
		display("-- val1=5, val2=10, distance="+Distances.absDistance(val1, val2));
		
	}
	
	public static void distanceSetTester(){
		
		Set<String> set1 = new HashSet<String>(Arrays.asList("A", "B", "C","D","E"));
		Set<String> set2 = new HashSet<String>();
		
		display("Set Symmetric difference: ");
		display("-- Addded 'A', 'B', 'C', 'D', 'E' to Set1, distance =" 
		+ Distances.distance(set1, set2));
		
		set2.addAll(Arrays.asList("C", "D", "E", "F", "G"));
		display("-- Addded 'C', 'D', 'E', 'F', 'G' to Set2, distance =" 
		+ Distances.distance(set1, set2));
	}
	
	
	public static void distanceORSetTester(){
		
		LocalClock clk1= new LocalClock("Site 1");
		LocalClock clk2= new LocalClock("Site 2");
		
		ORSet<String> set1=new ORSet<>();
		ORSet<String> set2=new ORSet<>(set1);
		
		display("ORSet Symmetric difference: ");
		
		set1.add("A", clk1);
		set1.add("B", clk1);
		set1.add("C", clk1);
		display("-- Added 'A', 'B', 'C' to Set1, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));
		
		set2.add("D", clk2);
		set2.add("E", clk2);
		display("-- Added 'D', 'E' to Set2, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));

		set1.remove("A", clk1);
		set1.remove("E", clk1);
		display("-- Removed 'A' and 'E' from Set1, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));
		
		set2.remove("E", clk2);
		display("-- Removed 'E' from Set2, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));

		try {
			set1.merge(set2, clk1.getCausalityClock(), clk2.getCausalityClock());
			display("-- Merging Set1 to Set2, distance=" + set1.distance(set2)+
					", and exact distance="+ set1.distanceExact(set2));
		} catch (IncompatibleTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		set1.remove("D", clk1);
		display("-- Removed 'D' from Set1, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));
		
		set2.remove("D", clk2);
		display("-- Removed 'D' from Set2, distance=" + set1.distance(set2)+
				", and exact distance="+ set1.distanceExact(set2));
		
		try {
			set1.merge(set2, clk1.getCausalityClock(), clk2.getCausalityClock());
			set2.merge(set1, clk2.getCausalityClock(), clk1.getCausalityClock());
			display("-- Merging Set2 to Set1, distance=" + set1.distance(set2)+
					", and exact distance="+ set1.distanceExact(set2));
		} catch (IncompatibleTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void distanceCounterTester(){
		
		LocalClock clk1= new LocalClock("Site 1");
		LocalClock clk2= new LocalClock("Site 2");
		
		Counter c1= new Counter();
		Counter c2=new Counter();
		display("Distance between Counters:");
		
		c1.add(3, clk1);
		display("-- inc 3 to c1, distance=" + c1.distance(c2));
		
		c2.add(2, clk2);
		display("-- inc 2 to c2, distance=" + c1.distance(c2));
		
		c1.sub(5, clk1);
		display("-- dec 5 from c1, distance=" + c1.distance(c2));
		
	}
	
	public static void absDistanceCounterTester(){
		
		LocalClock clk1= new LocalClock("Site 1");
		LocalClock clk2= new LocalClock("Site 2");
		
		Counter c1= new Counter();
		Counter c2=new Counter();
		display("Absolute distance between Counters:");
		
		c1.add(3, clk1);
		display("-- inc 3 to c1, distance=" + c1.absDistance(c2));
		
		c2.add(10, clk2);
		display("-- inc 10 to c2, distance=" + c1.absDistance(c2));

		
	}
	
	/**
	 * Displays an Object on std out.
	 * @param s
	 */
	public static void display(Object o){
		System.out.println(o);
	}
	
}
