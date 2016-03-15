package log.examples;

import java.io.IOException;



import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import log.formats.MoodleOperation;
import log.formats.model.MOODLE_OP;
import log.readers.SimpleLog;

public class SimpleExampleMoodle {

	public static void main(String[] args) throws ParseException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		
		SimpleLog<MOODLE_OP> log = new SimpleLog<MOODLE_OP>(args[0], MoodleOperation.getFactory());
		while (log.hasNext()) {
			System.out.println(log.next());
		}
	}
}
