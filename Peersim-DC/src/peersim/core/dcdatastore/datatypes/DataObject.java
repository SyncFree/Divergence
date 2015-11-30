package peersim.core.dcdatastore.datatypes;

public interface DataObject<D, M> extends Cloneable {

	public D getData();
	
	public void setData(D data);
	
	public void setData(D data, M metadata);
	
	public M getMetadata();
	
	public double computeDivergence(DataObject<?, ?> dataObject);
	
	public Object clone();
	
}
