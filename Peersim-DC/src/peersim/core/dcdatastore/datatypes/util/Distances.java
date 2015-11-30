package peersim.core.dcdatastore.datatypes.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Generic methods to compute divergence distances.
 * @author Ali Shoker
 *
 */
public class Distances {

	
	/**
	 * Calculates the distance between two integers. 
	 * It may return 
	 * -ve value if (first < second).
	 * @param first
	 * @param second
	 * @return -ve/+ve value of the difference.
	 */
	public static double distance(int first, int second){
		return first-second;
		
	}
	
	
	/**
	 * Calculates the absolute distance between two integers.
	 * @param val1
	 * @param val2
	 * @return absolute value of the difference.
	 */
	public static double absDistance(int val1, int val2){
		return Math.abs(val2-val1);
		
	}
	
	/**
	 * Calculates the distance (symmetric difference) between two sets.
	 * @param <T>
	 * @param set1
	 * @param set2
	 * @return the number of item of (Set1 v Set2) - (Set1 ^ Set2).
	 */
	public static <T> double distance(Set<T> set1, Set<T> set2){
		
		Set<T> symmetricDiff = new HashSet<T>(set1);
		symmetricDiff.addAll(set2); //union
		
		Set<T> intersection = new HashSet<T>(set1);
		intersection.retainAll(set2); //intersection
		
		symmetricDiff.removeAll(intersection); //union - intersection
		
		return symmetricDiff.size();
		
	}

	
	
}
