package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which in the Observer pattern is the object also known as the Subject.
 * For us to invoke the actions which we implemented the Subject must have the
 * ability to "talk" with our actions(also known as Concrete Observers). This is
 * achieved by prescribing the {@link IntegerStorageObserver}. The class holds a
 * private list of registered observers. Observers can be added or removed by
 * the user. The state of the Subject is considered to be changed once its value
 * is changed.
 * 
 * 
 * @author KarloFrühwirth
 *
 */
public class IntegerStorage {
	/**
	 * Integer value of the IntegerStorage
	 */
	private int value;
	/**
	 * List of registered observers
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>(); // use ArrayList here!!!

	/**
	 * Constructor for the IntegerStorage
	 * 
	 * @param initialValue
	 *            to which the value of the IntegerStorage is set
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Method used to add the observer in observers if it is not already there, if
	 * it is just skip this add.
	 * 
	 * @param observer
	 *            Observer which is attempted to be added to the observers list
	 */
	public void addObserver(IntegerStorageObserver observer) {
		boolean excists = false;

		for (IntegerStorageObserver o : observers) {
			if (o.equals(observer)) {
				excists = true;
			}
		}
		if (!excists) {
			observers.add(observer);
		}
	}

	/**
	 * Method used to remove an Observer if such exists
	 * 
	 * @param observer
	 *            Observer which is attempted to be removed from the observers list
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Removes all Observers from the observers list
	 */
	public void clearObservers() {
		observers.removeAll(observers);
	}

	/**
	 * Getter for the value of the IntegerStorage
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for the value of the IntegerStorage
	 * 
	 * @param value
	 *            to which the value is set
	 */
	public void setValue(int value) {
		List<IntegerStorageObserver> copyOfObservers = new ArrayList<>(observers);
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (copyOfObservers != null) {
				for (IntegerStorageObserver observer : copyOfObservers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}