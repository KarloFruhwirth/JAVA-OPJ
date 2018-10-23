package hr.fer.zemris.java.hw06.observer1;

/**
 * Interface that in the Observer pattern represents the Observer interface also
 * known as the Abstract Observer. It defines a method valueChanged which is
 * called when the state of the Subject ({@link IntegerStorage}) is changed.
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
	public void valueChanged(IntegerStorage istorage);
}