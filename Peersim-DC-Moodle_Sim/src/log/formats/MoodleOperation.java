package log.formats;

import static log.readers.AbstractLog.DELIMITER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import log.formats.model.MOODLE_OP;

public class MoodleOperation extends AbstractOperation<MOODLE_OP>
		implements
			OperationFactory<MOODLE_OP> {

	private static Logger log = Logger.getLogger(MoodleOperation.class
			.getName());

	private static String DEFAULT_TIME_FORMAT = "\"dd MMMMM yyyy, HH:mm\"";
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			DEFAULT_TIME_FORMAT);

	private MoodleOperation() {
		super();
	}

	private MoodleOperation(String courseId, long timestamp,
			String requesterId, String operation, String info, String objectId) {
		super(timestamp);
		this.attributes.put(MOODLE_OP.OPERATION, operation);
		this.attributes.put(MOODLE_OP.REQUESTER_ID, requesterId);
		this.attributes.put(MOODLE_OP.COURSE_ID, courseId);
		this.attributes.put(MOODLE_OP.INFO, info);
		this.attributes.put(MOODLE_OP.OBJECT_ID, "" + objectId);
	}

	public MoodleOperation parseLine(String nextLine) {
		String[] args = nextLine.split(DELIMITER);
		try {
			String courseId = args[0];
			long timestamp = formatter.parse(args[1]).getTime();
			String clientId = args[3];
			String operation = args[4];
			String info = args[5];
			String objectId = args.length > 6 ? args[6] : "";
			MoodleOperation op = new MoodleOperation(courseId, timestamp,
					clientId, operation, info, objectId);
			return op;
		} catch (ParseException e) {
			log.warning("Timestamp parsing failed: " + nextLine);
			return null;
		}
	}

	public static OperationFactory<MOODLE_OP> getFactory() {
		return new MoodleOperation();
	}

	public String toString() {
		return " REQUESTER " + attributes.get(MOODLE_OP.REQUESTER_ID) + " TS "
				+ timestamp + " COURSE_ID "
				+ attributes.get(MOODLE_OP.COURSE_ID) + " OP "
				+ attributes.get(MOODLE_OP.OPERATION) + " OBJECT_ID "
				+ attributes.get(MOODLE_OP.OBJECT_ID) + " INFO "
				+ attributes.get(MOODLE_OP.INFO);
	}

	public int compareTo(Operation<?> op) {
		if (op instanceof MoodleOperation) {
			MoodleOperation o = (MoodleOperation) op;
			if (Long.compare(this.getTimestampInMillis(), o.getTimestampInMillis()) == 0) {
				if (this.getAttributeByName(MOODLE_OP.REQUESTER_ID).compareTo(
						o.getAttributeByName(MOODLE_OP.REQUESTER_ID)) == 0) {
					return this
							.getAttributeByName(MOODLE_OP.REQUESTER_ID)
							.compareTo(
									o.getAttributeByName(MOODLE_OP.REQUESTER_ID));
				} else {
					return this.getAttributeByName(MOODLE_OP.OPERATION)
							.compareTo(
									o.getAttributeByName(MOODLE_OP.OPERATION));
				}
			} else {
				return Long.compare(this.getTimestampInMillis(), o.getTimestampInMillis());
			}
		} else {
			return this.getClass().getName().compareTo(op.getClass().getName());
		}
	}
}
