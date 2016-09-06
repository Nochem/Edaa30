package set;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class MaxSet<E extends Comparable<E>> extends ArraySet<E> {
	private E maxElement;

	/**
	 * Constructs a new empty set.
	 */
	public MaxSet() {
		super();
	}

	/**
	 * Returns the currently largest element in this set. pre: the set is not
	 * empty post: the set is unchanged
	 * 
	 * @return the currently largest element in this set
	 * @throws NoSuchElementException
	 *             if this set is empty
	 */
	public E getMax() {
		if (super.size()==0){
			throw new NoSuchElementException();
		}
		return maxElement;
	}

	/**
	 * Adds the specified element to this set, if it is not already present.
	 * post: x is added to the set if it is not already present
	 * 
	 * @param x
	 *            the element to be added
	 * @return true if the specified element was added
	 */
	public boolean add(E x) {
		if (super.size()==0){
			maxElement=x;
		} else if (x.compareTo(maxElement) > 0) {
			maxElement = x;
		}
		return super.add(x);

	}

	/**
	 * Removes the specified element from this set if it is present. post: x is
	 * removed if it was present
	 * 
	 * @param x
	 *            the element to remove - if present
	 * @return true if the set contained the specified element
	 */
	public boolean remove(Object x) { //Moms spaghetti. Also seems to contain an infinite loop.
		 if (super.remove(x)) {
			if (x.equals(maxElement)) {
				E temp;
				maxElement = super.iterator().next();
				while (super.iterator().hasNext()){ //I assume this is where the loop happends???
					temp = super.iterator().next(); 
					if (temp.compareTo(maxElement) > 0) {
						maxElement = temp;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Adds all of the elements in the specified set, for which it is possible,
	 * to this set. post: all elements, for which it is possible, in the
	 * specified set are added to this set.
	 * 
	 * @return true if this set changed as a result of the call
	 */
	public boolean addAll(SimpleSet<? extends E> c) {
		for (E x:c){
			if (x.compareTo(maxElement)>0){
				maxElement=x;
			}
		}
		return super.addAll(c);
	}

}
