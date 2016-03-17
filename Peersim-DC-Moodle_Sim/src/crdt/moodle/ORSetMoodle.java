package crdt.moodle;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import peersim.core.dcdatastore.datatypes.clocks.LocalClock;
import peersim.core.dcdatastore.datatypes.crdts.ORSet;


public class ORSetMoodle<T> extends ORSet<T> implements Set<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalClock clk;
	private String site;
	
	public ORSetMoodle(){
		super();
		site = UUID.randomUUID().toString(); // Ask Joao about getting the nodes name or sth.
		clk = new LocalClock(site);
	}
	public ORSetMoodle(ORSetMoodle<T> other){
		super(other);
		site = UUID.randomUUID().toString(); // Ask Joao about getting the nodes name or sth.
		clk = new LocalClock(site);
		
	}
	
	@Override
	public int size() {
		return super.getSize();
	}

	@Override
	public boolean isEmpty() {
		// What does it mean to be empty in a CRDT? 
		// is empty if there are tombstones?? 

		return super.getSize() == 0;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub

		return super.contains((T) o);
	}

	@Override
	public Iterator<T> iterator() {
		return super.iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T e) {
		return (super.add(e, clk));		
	}

	@Override
	public boolean remove(Object o) {
		return (super.remove((T)o, clk));
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
