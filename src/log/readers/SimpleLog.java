package log.readers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

import log.formats.Operation;
import log.formats.OperationFactory;

public class SimpleLog<T extends Enum<T>> extends AbstractLog<T> {

	long startTime;

	private static Logger log = Logger.getLogger(SimpleLog.class.getName());

	public SimpleLog(String pathToLog, OperationFactory<T> factory)
			throws ParseException, IOException {
		super(pathToLog, factory);
		init();
	}

	private boolean init() throws ParseException, IOException {
		stream = new Scanner(logFile);
		if (stream.hasNextLine()) {
			Operation<?> op = nextOp(stream.nextLine());
			startTime = op.getTimestamp();
			stream.reset();
			init = true;
		} else {
			stream.close();
		}
		log.info("Log " + logFile.getName() + " initilized successfully");
		return init;
	}

}
