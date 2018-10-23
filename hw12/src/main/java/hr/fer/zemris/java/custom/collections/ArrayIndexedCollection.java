package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * @author KarloFrühwirth
 * @version 1.0 Implementation of a resizable array-backed collection of objects
 *          It extends Collection and has 3 private
 *          variables(size,capacity,elements) Duplicate elements are allowed and
 *          the storage of null is not allowed
 */
public class ArrayIndexedCollection extends Collection {
	private int size = 0;
	private int capacity;
	private Object[] elements;
	private static final int DEFAULT = 16;

	/**
	 * Constructor that sets the capacity to the given param
	 * 
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Initial capacity of array must be greater than 1. Initial capacity was " + initialCapacity);
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * Default constructor that sets capacity to 16
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT);
	}

	/**
	 * Constructor that accepts another collection as a param and copies its
	 * elements to the current collection also it sets the capacity to the
	 * initialCapacity
	 * 
	 * @param collection
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(Math.max(collection.size(), initialCapacity));
		addAll(collection);
	}

	/**
	 * Constructor that accepts another collection as a param and copies its
	 * elements to the current collection
	 * 
	 * @param collection
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, DEFAULT);
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		System.arraycopy(elements, 0, array, 0, size);
		return array;
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Null elements are not allowed to be added to this collection.");
		}
		if (size == capacity) {
			capacity = capacity * 2;
			this.elements = Arrays.copyOf(elements, capacity * 2);
		}
		elements[size] = value;
		size++;
	}

	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		Arrays.fill(elements, null);
		size = 0;
	}

	/**
	 * Returns the object that is stored in backing array at position index
	 * 
	 * @param index
	 * @return object at index
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"The index that you entered is invalid. It should be between 0 and size-1=" + (this.size - 1));
		}
		return elements[index];
	}

	/**
	 * Inserts the given value at the given position in the array and shifts the
	 * other elements
	 * 
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Cant insert value at the given postion: " + position);
		} else if (position == size) {
			elements = Arrays.copyOf(elements, capacity * 2);
			elements[position] = value;
		} else {
			System.arraycopy(elements, position, elements, position + 1, size - position);
			this.elements[position] = value;
			this.size++;
		}
	}

	/**
	 * Returns the index of a certain Object if it is in the collection
	 * 
	 * @param value
	 * @return -1 || index of Object
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if (position < 0 || position > size - 1) {
			throw new IndexOutOfBoundsException("Cant remove value at the given postion: " + position);
		} else {
			System.arraycopy(elements, position + 1, elements, position, size - position);
			elements[size] = null;
			size--;
		}
	}

	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index == -1) {
			return false;
		} else {
			remove(index);
			return true;
		}
	}

}
