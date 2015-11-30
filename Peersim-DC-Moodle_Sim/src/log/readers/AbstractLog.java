package log.readers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

import log.formats.Operation;
import log.formats.OperationFactory;

public abstract class AbstractLog<T extends Enum<T>> implements Log<T> {

	public static String DELIMITER = " ";
	public static String NEW_LINE = "\n";

	protected File logFile;
	protected Scanner stream;
	protected boolean init;
	protected OperationFactory<T> factory;

	private static Logger log = Logger.getLogger(AbstractLog.class.getName());

	public AbstractLog(String pathToLog, OperationFactory<T> factory) throws ParseException, IOException {
		this.logFile = new File(pathToLog);
		this.factory = factory;
	}

	protected boolean init() throws ParseException, IOException {
		stream = new Scanner(logFile);
		if (stream.hasNextLine()) {
			init = true;
		} else {
			stream.close();
		}
		log.info("Log " + logFile.getName() + " initilized successfully");
		return init;
	}

	public Operation<T> nextOp(String line) throws ParseException {
		return factory.parseLine(line);
	}

	@Override
	public boolean hasNext() {
		return stream.hasNext();
	}

	@Override
	public Operation<T> next() {
		try {
			String line = stream.nextLine();
			return nextOp(line);
		} catch (ParseException e) {
			return null;
		}
	}

}
