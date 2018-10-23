package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Special kind of a map which allows the user to store multiple values for same
 * key. Also it provides a stack-like abstraction, the user can push,pop and
 * peek. Keys are instances of String while the value is a list of
 * {@link ValueWrapper}s
 * 
 * @author KarloFrühwirth
 *
 */
public class ObjectMultistack {
	/**
	 * Map which maps each key to first list node
	 */
	private Map<String, MultistackEntry> map = new HashMap<String, MultistackEntry>();

	/**
	 * Adds a new MultistackEntry to the map. If the string input is a key that is
	 * already stored in the map it appends the MultistackEntry to the end.
	 * otherwise it puts to the map under a new key this MultistackEntry
	 * 
	 * @param string
	 * @param year
	 */
	public void push(String string, ValueWrapper year) {
		if (map.containsKey(string)) {
			MultistackEntry current = map.get(string);
			while (current.next != null) {
				current = current.next;
			}
			current.next = new MultistackEntry(year, null);
		} else {
			map.put(string, new MultistackEntry(year, null));
		}

	}

	/**
	 * For the given key returns the last MultistackEntry
	 * 
	 * @param string
	 *            key
	 * @return last MultistackEntry for the given key
	 * @throws EmptyStackException
	 *             if the map is empty
	 * @throws IllegalArgumentException
	 *             if the key inputed doesn't exist
	 */
	public ValueWrapper peek(String string) {
		if (map.size() == 0) {
			throw new EmptyStackException();
		} else {
			if (map.containsKey(string)) {
				MultistackEntry current = map.get(string);
				while (current.next != null) {
					current = current.next;
				}
				return current.getValue();
			} else
				throw new IllegalArgumentException(
						"For the given key " + string + "there are no more MultistackEntrys");
		}
	}

	/**
	 * For the given key returns the last MultistackEntry and afterwards removes it.
	 * 
	 * @param string
	 *            key
	 * @return last MultistackEntry for the given key
	 * @throws EmptyStackException
	 *             if the map is empty
	 * @throws IllegalArgumentException
	 *             if the key inputed doesn't exist
	 */
	public ValueWrapper pop(String string) {
		if (map.size() == 0) {
			throw new EmptyStackException();
		}
		if (map.containsKey(string)) {
			MultistackEntry current = map.get(string);
			if (current.next == null) {
				ValueWrapper value = current.getValue();
				map.remove(string);
				return value;
			} else {
				while (current.next.next != null) {
					current = current.next;
				}
				ValueWrapper value = current.next.getValue();
				current.next = null;
				return value;
			}
		} else
			throw new IllegalArgumentException("For the given key " + string + "there are no more MultistackEntrys");

	}

	/**
	 * Checks if there is a MultistackEntry for the inputed key
	 * 
	 * @param name
	 *            key
	 * @return true | false
	 */
	public boolean isEmpty(String name) {
		if (map.get(name) != null) {
			return false;
		}
		return true;
	}

	/**
	 * Nested class which allows us to store multiple values under one key. Stores a
	 * reference to ValueWrapper object and a reference to next MultistackEntry
	 * object
	 * 
	 * @author KarloFrühwirth
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Value of the ValueWrapper
		 */
		private ValueWrapper value;
		/**
		 * Reference to the next MultistackEntry
		 */
		private MultistackEntry next;

		/**
		 * Constructor for the MultistackEntry
		 * 
		 * @param value
		 *            ValueWrapper
		 * @param next
		 *            MultistackEntry
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for the ValueWrapper value
		 * 
		 * @return value
		 */
		public ValueWrapper getValue() {
			return value;
		}
	}
}
