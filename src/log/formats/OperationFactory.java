package log.formats;

public interface OperationFactory<T extends Enum<T>> {

	public Operation<T> parseLine(String nextLine);

}
