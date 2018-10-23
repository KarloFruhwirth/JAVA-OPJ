package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represents an instance of a hash table used to store {@link TableEntry}
 * with its key and value. It implements Iterable and we can add, remove any
 * {@link TableEntry}
 * 
 * @author KarloFrühwirth
 *
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Default size of slots in the SimpleHashtable
	 */
	private static final int DEFAULT = 16;
	/**
	 * Number of elements in the SimpleHashtable
	 */
	private int size;
	/**
	 * Number of structural changes of the collection
	 */
	private int modificationCount;
	/**
	 * Array containing TableEntry elements
	 */
	private TableEntry<K, V>[] table;

	private int numberOfSlots;

	/**
	 * Constructor for the SimpleHashtable it sets the size of the TableEntry<K,
	 * V>[] table to the number that is a potential of 2 and is equal or greater
	 * than size
	 * 
	 * @throws IllegalArgumentException
	 *             if size is less than 1
	 * @param size
	 *            number for a desired size
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int size) {
		if (size < 1)
			throw new IllegalArgumentException("Size of the SimpleHashtable must be greather than 1!");
		int sizeNearestPowOfTwo = 1;
		while (sizeNearestPowOfTwo < size) {
			sizeNearestPowOfTwo *= 2;
		}
		this.table = (TableEntry<K, V>[]) new TableEntry[sizeNearestPowOfTwo];
		numberOfSlots = sizeNearestPowOfTwo;
	}

	/**
	 * Default constructor that sets the size of the TableEntry<K, V>[] table to 16
	 */
	public SimpleHashtable() {
		this(DEFAULT);
	}

	/**
	 * Method used to calculate the slot in which a TableEntry is in It is
	 * calculated based on the abs value of the key.hashCode which is then % 2
	 * 
	 * @param key
	 *            key for which we calculate the index
	 * @return slot index
	 */
	public int getIndex(Object key) {
		int tableSize = table.length;
		int index = Math.abs(key.hashCode());
		return index % tableSize;
	}

	/**
	 * Method that based on the key searches the table for that key and returns its
	 * value. If the key doesnt exist or the key is null the method returns null
	 * 
	 * @param key
	 *            Key for which we search
	 * @return null or value of TableEntry for the given key
	 */
	public V get(Object key) {
		if (key == null)
			throw new NullPointerException("Key must not be null");
		int index = getIndex(key);
		TableEntry<K, V> temp = table[index];
		while (true) {
			if (temp == null)
				break;
			else if (temp.getKey().equals(key)) {
				return temp.getValue();
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * Method that adds/changes a TableEntry If the TableEntry with the given key is
	 * present in the table it changes the value of that TableEntry If the
	 * TableEntry with the given key is not present in the table it adds a new
	 * TableEntry with the given key and value to the end
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key must not be null");
		int index = getIndex(key);
		TableEntry<K, V> input = new TableEntry<>(key, value, null);
		if (table[index] == null) {
			table[index] = input;
		} else {
			TableEntry<K, V> temp = table[index];
			while (true) {
				if (temp.getKey().equals(key)) {
					temp.setValue(value);
					return;
				} else if (temp.next == null) {
					temp.next = input;
					break;
				}
				temp = temp.next;
			}
		}
		size++;
		modificationCount++;
		if (size >= 0.75 * table.length) {
			changeSize();
		}
	}

	/**
	 * Changes the size of the TableEntry<K, V> table
	 */
	@SuppressWarnings("unchecked")
	private void changeSize() {
		size = 0;
		TableEntry<K, V>[] table2 = table;
		table = (TableEntry<K, V>[]) new TableEntry[2 * table2.length];
		for (TableEntry<K, V> e : table2) {
			TableEntry<K, V> temp = e;
			while (true) {
				if (temp == null)
					break;
				put(temp.getKey(), temp.getValue());
				if (temp.next == null)
					break;
				temp = temp.next;
			}
		}
	}

	/**
	 * Returns the number of TableEntrys in the table
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the number of slots in the table
	 * 
	 * @return numberOfSlots
	 */
	public int numberOfSlots() {
		return numberOfSlots;
	}

	/**
	 * Method that checks the table if it contains the key
	 * 
	 * @param key
	 *            Key being checked
	 * @return true || false based on the check
	 */
	public boolean containsKey(Object key) {
		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;
			while (true) {
				if (temp == null)
					break;
				else if (temp.getKey().equals(key)) {
					return true;
				} else if (temp.next == null)
					break;
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * Method that checks the table if it contains the value
	 * 
	 * @param value
	 *            Value being checked
	 * @return true || false based on the check
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;
			while (true) {
				if (temp == null)
					break;
				else if (temp.getValue().equals(value)) {
					return true;
				} else if (temp.next == null)
					break;
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * Method that removes a TableEntry if a TableEntry with the given key exists
	 * 
	 * @param key
	 *            Key
	 */
	public void remove(Object key) {
		if (containsKey(key)) {
			for (TableEntry<K, V> e : table) {
				TableEntry<K, V> temp = e;
				while (true) {
					if (temp == null)
						break;
					else if (temp.getKey().equals(key)) {
						this.table[getIndex(key)] = temp.next;
						size--;
						modificationCount++;
						return;
					} else if (temp.next == null)
						break;
					else if (temp.next.getKey().equals(key)) {
						if (temp.next.next == null) {
							temp.next = null;
							size--;
							modificationCount++;
							return;
						}
						temp.next = temp.next.next;
						size--;
						modificationCount++;
						return;
					}
					temp = temp.next;
				}
			}
		}
	}

	/**
	 * Checks if the table is empty or not
	 * 
	 * @return true||false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method that erases all TableEntrys from the table
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/**
	 * Inner class that is used to represent a TableEntry<K, V> used in the table of
	 * the {@link SimpleHashtable}. It is parameterized with a key K and a value V
	 * Provides necessary getters and setters
	 * 
	 * @author KarloFrühwirth
	 *
	 * @param <K>
	 *            parameter for the key
	 * @param <V>parameter
	 *            for the value
	 */
	public static class TableEntry<K, V> {
		/**
		 * Parameterized key
		 */
		private K key;
		/**
		 * Parameterized value
		 */
		private V value;
		/**
		 * points to the next TableEntry
		 */
		TableEntry<K, V> next;

		/**
		 * Constructor for TableEntry
		 * 
		 * @param key
		 *            key
		 * @param value
		 *            value
		 * @throws NullPointerException
		 *             if key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null)
				throw new NullPointerException("Key must not be null");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for value
		 * 
		 * @param value
		 *            Value to which it is set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for key
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}

	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class that represents our implementation of the Iterator
	 * 
	 * @author KarloFrühwirth
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {
		/**
		 * Number of elements iterated
		 */
		private int iterated = 0;
		/**
		 * Number of current slot being checked
		 */
		private int slotNumber = 0;
		/**
		 * Boolean that is used to check if multiple removes are called
		 */
		private boolean multipleRemoves = false;
		/**
		 * Inner modificationCount
		 */
		private int modificaions = modificationCount;
		/**
		 * TableEntry of the previous element
		 */
		private TableEntry<K, V> previous = null;
		/**
		 * TableEntry of the current element
		 */
		private TableEntry<K, V> current = null;

		public boolean hasNext() {
			if (modificaions != modificationCount)
				throw new ConcurrentModificationException("Internal modificationCount and outter are not the same");
			return iterated < size;
		}

		public TableEntry<K, V> next() {
			if (modificaions != modificationCount)
				throw new ConcurrentModificationException("Internal modificationCount and outter are not the same");
			if (hasNext()) {
				if (previous == null && current == null) {
					current = getTableEntry();
					iterated++;
				} else {
					previous = current;
					current = getTableEntry();
					iterated++;
				}
				multipleRemoves = false;
				return current;
			} else {
				throw new NoSuchElementException("There are no more elements in the collection");
			}
		}

		/**
		 * Used to return the the next element
		 * 
		 * @return TableEntry
		 */
		private TableEntry<K, V> getTableEntry() {
			while (table[slotNumber] == null)
				slotNumber++;
			if (current != null) {
				if (table[slotNumber].next != null) {
					return current.next;
				} else {
					slotNumber++;
					while (table[slotNumber] == null)
						slotNumber++;
					return table[slotNumber];
				}
			}
			return table[slotNumber];
		}

		public void remove() {
			if (modificaions != modificationCount)
				throw new ConcurrentModificationException("Internal modificationCount and outter are not the same");
			if (multipleRemoves)
				throw new IllegalStateException("Multiple iterator removes");
			multipleRemoves = true;
			SimpleHashtable.this.remove(current.getKey());
			current = previous;
			modificaions++;
			iterated--;
		}
	}

}
