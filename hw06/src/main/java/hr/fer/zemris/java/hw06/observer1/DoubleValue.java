package hr.fer.zemris.java.hw06.observer1;

/**
 * Concrete Observer which doubles the value of the {@link IntegerStorage}.value
 * 
 * @author KarloFrühwirth
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Constant used for incrementing the value
	 */
	private static final int VALUE = 2;
	/**
	 * Number of iterations
	 */
	private int numberOfIterations;
	/**
	 * Counter of the iterations
	 */
	private int counter = 0;

	/**
	 * Constructor for the DoubleValue
	 * 
	 * @param n
	 *            to which the numberOfIterations is set
	 */
	public DoubleValue(int n) {
		numberOfIterations = n;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Prints out to the standard output the doubled value. It removes it self from
	 * the IntegerStorage once the counter is the same as the numberOfIterations
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (counter == numberOfIterations) {
			istorage.removeObserver(this);
		} else {
			counter++;
			System.out.println("Double value: " + doubleValue(istorage.getValue()));
		}
	}

	/**
	 * Method used to double the value of the argument
	 * 
	 * @param i
	 *            value of the IntegerStorage.value
	 * @return double value
	 */
	private int doubleValue(int i) {
		return i * VALUE;
	}

}
