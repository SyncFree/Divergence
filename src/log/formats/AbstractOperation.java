package log.formats;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AbstractOperation<T extends Enum<?>>
		implements
			Operation<T> {

	protected Map<T, String> attributes;
	protected long timestamp;

	protected static Logger log = Logger.getLogger(AbstractOperation.class
			.getName());

	protected static String DEFAULT_TIME_FORMAT = "\"dd MMMMM yyyy, kk:mm\"";
	protected static SimpleDateFormat formatter = new SimpleDateFormat(
			DEFAULT_TIME_FORMAT);

	protected AbstractOperation() {
	}

	public AbstractOperation(long timestamp) {
		super();
		this.attributes = new HashMap<>();
		this.timestamp = timestamp;
	}

	public abstract Operation<T> parseLine(String nextLine);

	@Override
	public String getAttributeByName(T name) {
		return attributes.get(name);
	}

	public long getTimestampInMillis() {
		return timestamp;
	}

	public String toString() {
		return " REQUESTER " + " TS " + timestamp;
	}

}
