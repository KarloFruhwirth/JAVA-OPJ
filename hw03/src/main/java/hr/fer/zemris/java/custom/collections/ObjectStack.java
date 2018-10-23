package hr.fer.zemris.java.custom.collections;

/**
 * @author KarloFrühwirth
 * @version 1.0
 * 
 *          Class ObjectStack is the Adaptor in used design pattern it provides
 *          the user the methods defined for a stack and uses
 *          ArrayIndexedCollection to get the stack format
 */
public class ObjectStack {
	private ArrayIndexedCollection col = new ArrayIndexedCollection();

	/**
	 * Returns true if collection is empty otherwise false
	 * 
	 * @return true|false
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}

	/**
	 * Returns the size of the stack
	 * 
	 * @return size
	 */
	public int size() {
		return col.size();
	}

	/**
	 * Add Object to the stack
	 * 
	 * @param value
	 */
	public void push(Object value) {
		col.add(value);
	}

	/**
	 * Removes the top Object from the stack
	 * 
	 * @return top Object
	 */
	public Object pop() {
		if (col.size() == 0) {
			throw new EmptyStackException();
		}
		Object rez = col.get(col.size() - 1);
		col.remove(col.size() - 1);
		return rez;
	}

	/**
	 * Unlike pop,peek only returns the Object at the top of the stack withouth
	 * removing it
	 * 
	 * @return
	 */
	public Object peek() {
		if (col.size() == 0) {
			throw new EmptyStackException();
		}
		return col.get(col.size() - 1);
	}
}
