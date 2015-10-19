package log.formats;

public interface Operation<T extends Enum<?>> extends Comparable<Operation<?>> {

	public long getTimestampInMillis();

	String getAttributeByName(T name);

}
