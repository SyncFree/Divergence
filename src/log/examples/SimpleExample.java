package log.examples;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import log.formats.MoodleOperation;
import log.readers.SimpleLog;

public class SimpleExample {

	public static void main(String[] args) throws ParseException, IOException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		SimpleLog log = new SimpleLog(args[0], MoodleOperation.getFactory());
		while (log.hasNext()) {
			System.out.println(log.next());
		}

	}
}
