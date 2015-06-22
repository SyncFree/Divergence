package log.examples;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import log.formats.MoodleOperation;
import log.readers.FilteredLog;
import log.readers.SimpleLog;

public class FilterExample {

	public static void main(String[] args) throws ParseException, IOException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		FilteredLog log = new FilteredLog(new SimpleLog(args[0],
				MoodleOperation.getFactory()));
		log.addFilter("requesterId", "USER_354509214", "USER_1569589921");

		// Clears Previous filters
		// log.addFilter("requesterId", "*");
		while (log.hasNext()) {
			System.out.println(log.next());
		}

	}
}
