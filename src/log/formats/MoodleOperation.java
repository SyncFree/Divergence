package log.formats;

import static log.readers.AbstractLog.DELIMITER;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class MoodleOperation extends AbstractOperation
		implements
			Operation,
			OperationFactory {

	private static Logger log = Logger.getLogger(MoodleOperation.class
			.getName());

	private static String DEFAULT_TIME_FORMAT = "\"dd MMMMM yyyy, kk:mm\"";
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			DEFAULT_TIME_FORMAT);

	private MoodleOperation() {
		super();
	}

	private MoodleOperation(String courseId, long timestamp,
			String requesterId, String operation, String info, String objectId) {
		super(timestamp, requesterId, operation);
		this.attributes.put("courseId", courseId);
		this.attributes.put("info", info);
		this.attributes.put("objectId", "" + objectId);
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

	public static OperationFactory getFactory() {
		return new MoodleOperation();
	}

	public String toString() {
		return " REQUESTER " + attributes.get("requesterId") + " TS "
				+ timestamp + " COURSE_ID " + attributes.get("courseId")
				+ " OP " + attributes.get("operation") + " OBJECT_ID "
				+ attributes.get("objectId") + " INFO "
				+ attributes.get("info");
	}
}
