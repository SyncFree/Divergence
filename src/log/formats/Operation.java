package log.formats;

public interface Operation<T extends Enum<?>> {

	public long getTimestamp();

	String getAttributeByName(T name);

}
