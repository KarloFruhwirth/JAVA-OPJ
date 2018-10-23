package hr.fer.zemris.java.hw06.observer1;

/**
 * Concrete Observer which counts the number of times the value of the
 * {@link IntegerStorage}.value is changed
 * 
 * @author KarloFrühwirth
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Number of changes
	 */
	private int numberOfChanges;

	/**
	 * {@inheritDoc} Prints out the number of changes
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		numberOfChanges++;
		System.out.println("Number of value changes since tracking: " + numberOfChanges);
	}

}
