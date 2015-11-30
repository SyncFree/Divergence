package log.readers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import log.formats.Operation;

public class FilteredLog<T extends Enum<T>> implements Log<T> {

	private Log<T> logReader;
	private Operation<T> nextOp;

	private Map<T, Set<String>> filter;

	public FilteredLog(Log<T> logReader) {
		this.logReader = logReader;
		this.filter = new HashMap<>();
	}
	public void addFilter(T attribute, String value) {
		if (!filter.containsKey(attribute) || value.equals("*")) {
			filter.put(attribute, new HashSet<>());
		}
		filter.get(attribute).add(value);
	}

	public void addFilter(T attribute, String... values) {

		if (!filter.containsKey(attribute)) {
			filter.put(attribute, new HashSet<>());
		}

		Set<String> filterValues = filter.get(attribute);

		for (String value : values) {
			if (value.equals("*")) {
				filterValues.clear();
			} else {
				filterValues.add(value);
			}
		}
	}

	@Override
	public boolean hasNext() {
		computeNextOp();
		if (nextOp == null)
			return false;
		return true;

	}
	private void computeNextOp() {
		while (nextOp == null) {
			if (logReader.hasNext()) {
				Operation<T> streamNext = logReader.next();
				if (filterValue(streamNext)) {
					nextOp = streamNext;
				}
			} else {
				break;
			}
		}
	}

	private boolean filterValue(Operation<T> op) {
		boolean ok = false;
		if (filter.size() == 0) {
			ok = true;
		} else {
			for (Entry<T, Set<String>> attrFilter : filter.entrySet()) {
				if (attrFilter.getValue().size() == 0
						|| attrFilter.getValue().contains(
								op.getAttributeByName(attrFilter.getKey())))
					ok = true;
			}
		}
		return ok;
	}
	@Override
	public Operation<T> next() {
		if (nextOp == null)
			computeNextOp();
		Operation<T> opToReturn = nextOp;
		nextOp = null;
		return opToReturn;

	}

}
