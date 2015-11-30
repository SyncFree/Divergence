package log.readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

import log.formats.Operation;
import log.formats.OperationFactory;

public class SimpleLog<T extends Enum<T>> extends AbstractLog<T> {

	private long startTime;

	private static Logger log = Logger.getLogger(SimpleLog.class.getName());

	public SimpleLog(String pathToLog, OperationFactory<T> factory) throws ParseException, IOException {
		super(pathToLog, factory);
		init();
	}

	@Override
	protected boolean init() throws ParseException, IOException {
		stream = new Scanner(logFile);
		if (stream.hasNextLine()) {
			Operation<?> op = nextOp(stream.nextLine());
			startTime = op.getTimestampInMillis();
			stream.close();
			stream = new Scanner(logFile);
			init = true;
		} else {
			stream.close();
		}
		log.info("Log " + logFile.getName() + " initilized successfully");
		return init;
	}

	public long getStartTime() {
		return startTime;
	}

	public void resetState() {
		stream.close();
		try {
			stream = new Scanner(logFile);
		} catch (FileNotFoundException e) {
			log.severe("Problem openning scanner on file. Maybe it was removed");
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
