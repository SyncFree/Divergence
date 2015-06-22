package log.formats;

public interface OperationFactory {

	public Operation parseLine(String nextLine);

}
