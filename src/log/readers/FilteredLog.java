package log.readers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import log.formats.Operation;

public class FilteredLog implements Log {

	private Log logReader;
	private Operation nextOp;

	private Map<String, Set<String>> filter;

	public FilteredLog(Log logReader) {
		this.logReader = logReader;
		this.filter = new HashMap<>();
	}
	public void addFilter(String attribute, String value) {
		if (!filter.containsKey(attribute) || value.equals("*")) {
			filter.put(attribute, new HashSet<>());
		}
		filter.get(attribute).add(value);
	}

	public void addFilter(String attribute, String... values) {

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
				Operation streamNext = logReader.next();
				if (filterValue(streamNext)) {
					nextOp = streamNext;
				}
			} else {
				break;
			}
		}
	}

	private boolean filterValue(Operation op) {
		for (Entry<String, Set<String>> attrFilter : filter.entrySet()) {
			if (attrFilter.getValue().size() == 0
					|| attrFilter.getValue().contains(
							op.getAttributeByName(attrFilter.getKey())))
				return true;
		}
		return false;
	}
	@Override
	public Operation next() {
		if (nextOp == null)
			computeNextOp();
		Operation opToReturn = nextOp;
		nextOp = null;
		return opToReturn;

	}

}
