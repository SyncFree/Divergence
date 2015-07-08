package log.formats;

public interface Operation<T extends Enum<?>> extends Comparable<Operation<?>> {

	public long getTimestamp();

	String getAttributeByName(T name);

}
