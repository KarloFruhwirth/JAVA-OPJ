package hr.fer.zemris.java.custom.collections;

/**
 * @author KarloFrühwirth
 * @version 1.0 Defines a representation of a collection and all of its
 *          supported methods
 */
public class Collection {

	/**
	 * Protected default constructor
	 */
	protected Collection() {
	}

	/**
	 * @return true if collection contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * @return number of currently stored objects in this collections
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection
	 * 
	 * @param value
	 */
	public void add(Object value) {
	}

	/**
	 * @param value
	 * @return true only if the collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method that removes one occurrence of a certain value if it contains the
	 * given value
	 * 
	 * @param value
	 * @return true only if the collection contains given value
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that allocates new array with size equals to the size of this
	 * collections, fills it with collection content and returns the array.
	 * 
	 * @return
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process for each element of this collection
	 * 
	 * @param processor
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection
	 * 
	 * @param other
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new LocalProcessor());
	}

	/**
	 * Removes all elements from this collection
	 */
	public void clear() {

	}
}
