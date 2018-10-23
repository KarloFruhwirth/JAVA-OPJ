package hr.fer.zemris.java.hw06.observer2;

/**
 * Interface that in the Observer pattern represents the Observer interface also
 * known as the Abstract Observer. It defines a method valueChanged which now
 * contains unlike in the first one a reference to an instance of
 * {@link IntegerStorageChange} class.
 * 
 * @author KarloFrühwirth
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method which is called when the state of the Subject({@link IntegerStorage})
	 * is changed.
	 * 
	 * @param istorage
	 *            reference to the Subject
	 */
	public void valueChanged(IntegerStorageChange istorage);
}