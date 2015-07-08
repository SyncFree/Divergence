package log.examples;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import log.formats.MoodleOperation;
import log.formats.Operation;
import log.formats.model.MOODLE_OP;
import log.readers.FilteredLog;
import log.readers.SimpleLog;

public class FilterExample {

	public static void main(String[] args) throws ParseException, IOException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		FilteredLog<MOODLE_OP> log = new FilteredLog<>(new SimpleLog<>(args[0],
				MoodleOperation.getFactory()));
		log.addFilter(MOODLE_OP.REQUESTER_ID, "USER_354509214",
				"USER_1569589921");

		// Clears Previous filters
		// log.addFilter("requesterId", "*");
		while (log.hasNext()) {
			Operation<MOODLE_OP> op = log.next();
			String out = op.getAttributeByName(MOODLE_OP.COURSE_ID);
			out += " " + op.getAttributeByName(MOODLE_OP.REQUESTER_ID);
			out += " " + op.getTimestamp();
			System.out.println(out);
		}

	}
}
