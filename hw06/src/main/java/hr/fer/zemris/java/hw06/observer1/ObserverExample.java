package hr.fer.zemris.java.hw06.observer1;

/**
 * Example which demonstrates the functionality of the
 * {@link IntegerStorage}(Subject) and its Concrete Observers
 * ({@link DoubleValue}, {@link SquareValue}, {@link ChangeCounter})
 * 
 * @author KarloFrühwirth
 *
 */
public class ObserverExample {
	/**
	 * Main method used to run the example
	 * 
	 * @param args
	 *            not used for the purpose of this example
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
