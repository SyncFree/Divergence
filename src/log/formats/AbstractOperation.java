package log.formats;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AbstractOperation implements Operation {

	protected Map<String, String> attributes;
	protected long timestamp;

	protected static Logger log = Logger.getLogger(AbstractOperation.class
			.getName());

	protected static String DEFAULT_TIME_FORMAT = "\"dd MMMMM yyyy, kk:mm\"";
	protected static SimpleDateFormat formatter = new SimpleDateFormat(
			DEFAULT_TIME_FORMAT);

	protected AbstractOperation() {
	}

	public AbstractOperation(long timestamp, String requesterId,
			String operation) {
		super();
		this.attributes = new HashMap<>();
		this.timestamp = timestamp;
		this.attributes.put("requesterId", requesterId);
		this.attributes.put("operation", operation);
		this.attributes.put("timestamp", "" + timestamp);

	}

	public abstract AbstractOperation parseLine(String nextLine);

	public String getAttributeByName(String name) {
		return attributes.get(name);
	}

	public String getRequesterId() {
		return this.attributes.get("requesterId");
	}

	public String getOperation() {
		return this.attributes.get("operation");
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return " REQUESTER " + attributes.get("requesterId") + " TS "
				+ timestamp + " OP " + attributes.get("operation");
	}

}
