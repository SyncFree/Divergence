package log.formats;

public interface Operation {

	public String getAttributeByName(String name);

	public String getRequesterId();

	public String getOperation();

	public long getTimestamp();

}
