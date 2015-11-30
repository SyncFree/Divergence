package log.formats;

import static log.readers.AbstractLog.DELIMITER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import log.formats.model.AD_OP;

public class AdServiceOperation extends AbstractOperation<AD_OP> implements OperationFactory<AD_OP> {

	private static Logger log = Logger.getLogger(AdServiceOperation.class.getName());

	private static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss,S";
	private static SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

	private AdServiceOperation() {
		super();
	}

	private AdServiceOperation(long timestamp, String key, String opType) {
		super(timestamp);
		this.attributes.put(AD_OP.KEY, key);
		this.attributes.put(AD_OP.OPERATION, opType);
	}

	@Override
	public AdServiceOperation parseLine(String nextLine) {
		String[] args = nextLine.split(DELIMITER);
		try {
			String key = args[2];
			String operation = args[3];
			long timestamp = formatter.parse(args[0] + " " + args[1]).getTime();
			AdServiceOperation op = new AdServiceOperation(timestamp, key, operation);
			return op;
		} catch (ParseException e) {
			log.warning("Timestamp parsing failed: " + nextLine);
			return null;
		}
	}

	public static OperationFactory<AD_OP> getFactory() {
		return new AdServiceOperation();
	}

	@Override
	public String toString() {
		return " TIMESTAMP " + timestamp + " KEY " + attributes.get(AD_OP.KEY) + " OP "
				+ attributes.get(AD_OP.OPERATION);
	}

	@Override
	public int compareTo(Operation<?> op) {
		if (op instanceof AdServiceOperation) {
			AdServiceOperation o = (AdServiceOperation) op;
			if (Long.compare(this.getTimestampInMillis(), o.getTimestampInMillis()) == 0) {
				if (this.getAttributeByName(AD_OP.KEY).compareTo(o.getAttributeByName(AD_OP.KEY)) == 0) {
					return this.getAttributeByName(AD_OP.OPERATION).compareTo(o.getAttributeByName(AD_OP.OPERATION));
				} else {
					return this.getAttributeByName(AD_OP.KEY).compareTo(o.getAttributeByName(AD_OP.KEY));
				}
			} else {
				return Long.compare(this.getTimestampInMillis(), o.getTimestampInMillis());
			}
		} else {
			return this.getClass().getName().compareTo(op.getClass().getName());
		}
	}
}
