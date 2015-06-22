package log.examples;

import java.io.IOException;
import java.text.ParseException;

import log.formats.MoodleOperation;
import log.readers.LogByTime;

public class OrderExample {

	public static void main(String[] args) throws ParseException, IOException {
		LogByTime log = new LogByTime(args[0], MoodleOperation.getFactory());
		while (log.hasNext()) {
			System.out.println(log.next());
		}

	}

}
