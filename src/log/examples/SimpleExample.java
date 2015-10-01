package log.examples;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import log.formats.AdServiceOperation;
import log.formats.model.AD_OP;
import log.readers.SimpleLog;

public class SimpleExample {

	public static void main(String[] args) throws ParseException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException {

		SimpleLog<AD_OP> log = new SimpleLog<AD_OP>(args[0], AdServiceOperation.getFactory());
		while (log.hasNext()) {
			System.out.println(log.next());
		}

	}
}
