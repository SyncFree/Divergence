package log.examples;

import java.util.Comparator;

public class Pair<F extends Comparable<F>, S extends Comparable<S>> implements Comparable<Pair<F, S>> {

	final F first;
	final S second;
	final Comparator<Pair<F, S>> comparator;

	final Comparator<Pair<F, S>> DESC_BY_SECOND = new Comparator<Pair<F, S>>() {

		@Override
		public int compare(Pair<F, S> o1, Pair<F, S> o2) {
			return -(o1.second.compareTo(o2.second) != 0 ? o1.second.compareTo(o2.second) : o2.first
					.compareTo(o2.first));
		}
	};

	final Comparator<Pair<F, S>> ASC_BY_SECOND = new Comparator<Pair<F, S>>() {

		@Override
		public int compare(Pair<F, S> o1, Pair<F, S> o2) {
			return o1.second.compareTo(o2.second) != 0 ? o1.second.compareTo(o2.second) : o2.first.compareTo(o2.first);
		}
	};

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
		this.comparator = ASC_BY_SECOND;
	}

	public Pair(F first, S second, boolean descending) {
		this.first = first;
		this.second = second;
		this.comparator = DESC_BY_SECOND;
	}

	@Override
	public int hashCode() {
		return (first + "" + second).hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair otherP = (Pair) other;
			return first.equals(otherP.first) && second.equals(otherP.second);
		}
		return false;
	}

	@Override
	public int compareTo(Pair<F, S> o) {
		return comparator.compare(this, o);
	}

	@Override
	public String toString() {
		return "{ " + first + ", " + second + " }";
	}
}