package log.parsing;

import static log.readers.AbstractLog.DELIMITER;
import static log.readers.AbstractLog.NEW_LINE;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anonymizer {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Expected argument: LOG_FILE.\n "
					+ "Received args: " + Arrays.toString(args));
			System.exit(0);
		}
		String filename = args[0];
		File file = new File(filename);
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineArgs = line.split(DELIMITER);

			if (lineArgs.length > 6 || lineArgs.length < 5) {
				System.err.println("SKIP LINE: " + line);
				continue;
			}

			StringBuilder outputLine = new StringBuilder();
			outputLine.append("COURSE_" + lineArgs[0].hashCode());
			outputLine.append(DELIMITER);
			outputLine.append(lineArgs[1]);
			outputLine.append(DELIMITER);
			outputLine.append(lineArgs[2]);
			outputLine.append(DELIMITER);
			outputLine.append("USER_" + lineArgs[3].hashCode());
			outputLine.append(DELIMITER);
			int urlPos = lineArgs[4].lastIndexOf("http://");
			outputLine.append("\""
					+ lineArgs[4].substring(1, urlPos - 1).trim() + "\""); // action
			outputLine.append(DELIMITER);

			String url = lineArgs[4]
					.substring(urlPos, lineArgs[4].length() - 1);
			// String[] urlArgs = url.split("\\?");
			//
			// outputLine.append("URL_" + urlArgs[0].hashCode()); // URL
			// if (urlArgs.length > 1) {
			// outputLine.append("-" + urlArgs[1].hashCode()); // URL args.
			// }

			outputLine.append(getUrlParameters(url)); // URL

			if (lineArgs.length == 6) {
				outputLine.append(DELIMITER);
				outputLine.append("OBJ_" + lineArgs[5].hashCode());
			}
			outputLine.append(NEW_LINE);

			System.out.print(outputLine);

		}

		scanner.close();

	}

	private static String getUrlParameters(String Url) {
		Matcher matcher = Pattern.compile("[0-9][0-9]*").matcher(Url);
		String result = "";

		while (matcher.find()) {
			result += (Integer.parseInt(matcher.group(0))) + ",";
		}
		return result.substring(0, result.length() - 1);
	}
}
