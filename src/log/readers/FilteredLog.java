package log.readers;

import java.util.Set;
import java.util.TreeSet;

import log.formats.Operation;

public class FilteredLog implements Log {

	private Log logReader;
	private Operation nextOp;

	/*
	 * TODO: GENERIC SUPPORT FOR FILTERS
	 */
	private Set<String> clients;

	public FilteredLog(Log logReader) {
		this.logReader = logReader;
		this.clients = new TreeSet<>();
	}
	public void addFilter(String attribute, String value) {
		if (attribute.equals("requesterId")) {
			if (value.equals("*")) {
				clients.clear();
			} else {
				clients.add(value);
			}
		}
	}

	public void addFilter(String attribute, String... values) {
		if (attribute.equals("requesterId")) {
			for (String value : values)
				if (value.equals("*")) {
					clients.clear();
				} else {
					clients.add(value);
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
				if (clients.contains(streamNext
						.getAttributeByName("requesterId"))
						|| clients.size() == 0) {
					nextOp = streamNext;
				}
			} else {
				break;
			}
		}
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
