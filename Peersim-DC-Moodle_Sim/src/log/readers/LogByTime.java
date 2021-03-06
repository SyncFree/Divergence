package log.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.Logger;

import log.formats.Operation;
import log.formats.OperationFactory;

public class LogByTime<T extends Enum<T>> extends AbstractLog<T> {

	long startTime;

	private static Logger log = Logger.getLogger(LogByTime.class.getName());

	public LogByTime(String pathToLog, OperationFactory<T> factory) throws ParseException, IOException {
		super(pathToLog, factory);
		this.logFile = orderByTimeLog(logFile, factory);
		init();
	}

	@Override
	protected boolean init() throws ParseException, IOException {
		stream = new Scanner(logFile);
		if (stream.hasNextLine()) {
			Operation<T> op = nextOp(stream.nextLine());
			startTime = op.getTimestampInMillis();
			stream.reset();
			init = true;
		} else {
			stream.close();
		}
		log.info("Log " + logFile.getName() + " initilized successfully");
		return init;
	}

	private static File orderByTimeLog(File logFile, final OperationFactory<?> factory) throws IOException {
		final Map<String, Operation<?>> ops = new HashMap<String, Operation<?>>();
		PriorityQueue<String> orderedFile = new PriorityQueue<String>(new Comparator<String>() {

			public int compare(String o1, String o2) {
				Operation<?> op1 = ops.get(o1);
				if (op1 == null) {
					op1 = factory.parseLine(o1);
					ops.put(o1, op1);
				}

				Operation<?> op2 = ops.get(o2);
				if (op2 == null) {
					op2 = factory.parseLine(o2);
					ops.put(o2, op2);
				}

				return op1.compareTo(op2);
			}
		});

		Scanner scanner = new Scanner(logFile);
		while (scanner.hasNextLine()) {
			orderedFile.add(scanner.nextLine());
		}
		scanner.close();
		File tmp = File.createTempFile("ordered-" + logFile.getName() + "_", null);
		FileOutputStream fileWriter = new FileOutputStream(tmp);
		while (orderedFile.size() > 0) {
			fileWriter.write((orderedFile.remove() + "\n").getBytes());
		}
		fileWriter.close();
		return tmp;
	}

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
	ClassNotFoundException {

		if (args.length < 2 || args.length > 2) {
			System.err
			.println("Bad args: arg1: logfile arg2: Factory Class name arg3: (optional): output filename. default stdout");
		}

		FileInputStream is;
		OutputStream os;
		byte[] buffer = new byte[4096];

		Class<?> c = Class.forName(args[1]);

		File orderedLog = orderByTimeLog(new File(args[0]), (OperationFactory<?>) c.getMethod("getFactory")
				.invoke(null));

		is = new FileInputStream(orderedLog);

		if (args.length == 2) {
			os = System.out;
		} else {
			os = new FileOutputStream(new File(args[2]));
		}
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		is.close();
		os.close();

	}
}
