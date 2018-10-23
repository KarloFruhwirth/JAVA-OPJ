package hr.fer.zemris.java.hw06.observer2;

/**
 * Class that encapsulates a reference to the {@link IntegerStorage}, the value
 * before the change occurred and the new value of the currently stored integer.
 * 
 * @author KarloFrühwirth
 *
 */
public class IntegerStorageChange {
	/**
	 * Reference to the IntegerStorage
	 */
	private IntegerStorage interStorage;
	/**
	 * Value of the integer before the change
	 */
	private int beforeChange;
	/**
	 * Value of the integer after the change
	 */
	private int afterChange;

	/**
	 * Constructor for the IntegerStorageChange which sets the values for
	 * interStorage, beforeChange and afterChange
	 * 
	 * @param interStorage
	 *            IntegerStorage
	 * @param beforeChange
	 *            int value
	 * @param afterChange
	 *            int value
	 */
	public IntegerStorageChange(IntegerStorage interStorage, int beforeChange, int afterChange) {
		this.interStorage = interStorage;
		this.beforeChange = beforeChange;
		this.afterChange = afterChange;
	}

	/**
	 * Getter for the IntegerStorage
	 * 
	 * @return interStorage
	 */
	public IntegerStorage getInterStorage() {
		return interStorage;
	}

	/**
	 * Getter for the beforeChange
	 * 
	 * @return beforeChange
	 */
	public int getBeforeChange() {
		return beforeChange;
	}

	/**
	 * Getter for the afterChange
	 * 
	 * @return afterChange
	 */
	public int getAfterChange() {
		return afterChange;
	}

}
