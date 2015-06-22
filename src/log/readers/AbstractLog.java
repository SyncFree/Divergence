package log.readers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;

import log.formats.Operation;
import log.formats.OperationFactory;

public abstract class AbstractLog implements Log {

	public static String DELIMITER = "\t";
	public static String NEW_LINE = "\n";

	protected File logFile;
	protected Scanner stream;
	protected boolean init;
	protected OperationFactory factory;

	private static Logger log = Logger.getLogger(AbstractLog.class.getName());

	public AbstractLog(String pathToLog, OperationFactory factory)
			throws ParseException, IOException {
		this.logFile = new File(pathToLog);
		this.factory = factory;
		init();
	}

	private boolean init() throws ParseException, IOException {
		stream = new Scanner(logFile);
		if (stream.hasNextLine()) {
			init = true;
		} else {
			stream.close();
		}
		log.info("Log " + logFile.getName() + " initilized successfully");
		return init;
	}

	public Operation nextOp(String line) throws ParseException {
		return factory.parseLine(line);
	}

	@Override
	public boolean hasNext() {
		return stream.hasNext();
	}

	@Override
	public Operation next() {
		try {
			String line = stream.nextLine();
			return nextOp(line);
		} catch (ParseException e) {
			return null;
		}
	}

}
