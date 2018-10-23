package hr.fer.zemris.java.hw06.observer2;

/**
 * Concrete Observer which squares the value of the {@link IntegerStorage}.value
 * 
 * @author KarloFrühwirth
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc}
	 * 
	 * Prints out the newly provided value and the square of that value
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Provided new value: " + istorage.getAfterChange() + ", square is "
				+ square(istorage.getAfterChange()));

	}

	/**
	 * Method used to square the value of the argument
	 * 
	 * @param value
	 *            of the IntegerStorage.value
	 * @return square value
	 */
	private int square(int value) {
		return value * value;
	}

}
