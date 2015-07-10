package peersim.core.dcdatastore.util;

public interface DataObject<D, M> {

	public D getData();
	
	public void setData(D data);
	
	public void setData(D data, M metadata);
	
	public M getMetadata();
	
	public double computeDivergence(DataObject<?, ?> dataObject);
	
}
