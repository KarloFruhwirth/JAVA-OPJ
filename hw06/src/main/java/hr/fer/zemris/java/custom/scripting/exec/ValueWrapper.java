package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class which is used in {@link ObjectMultistack}. It is used represent the
 * value which is stored under a certain key. The Object which is its value must
 * be null||String||Integer||Double. There are 4 operations prescribed for the
 * ValueWrappers, and they are : add, subtract, multiply and divide. There is
 * also an additional numerical comparison method numCompare.
 * 
 * 
 * @author KarloFrühwirth
 *
 */
public class ValueWrapper {
	/**
	 * Value of the ValueWrapper
	 */
	private Object value;

	/**
	 * Constructor for the ValueWrapper
	 * 
	 * @param value
	 *            Value to which the ValueWrapper value is set if it is
	 *            null||String||Integer||Double.
	 * @throws IllegalArgumentException
	 *             if the provided value is not null||String||Integer||Double
	 */
	public ValueWrapper(Object value) {
		if (value == null || value instanceof String || value instanceof Double || value instanceof Integer) {
			this.value = value;
		} else {
			throw new IllegalArgumentException("Value of the object must be null||String||Integer||Double");
		}
	}

	/**
	 * Getter for value
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for the value
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Modifies the current value by adding to it incValue
	 * 
	 * @param incValue
	 *            argument
	 */
	public void add(Object incValue) {
		Number arg1 = checkAndSetArgument(value);
		Number arg2 = checkAndSetArgument(incValue);
		if (areBothInteger(arg1, arg2)) {
			setValue(arg1.intValue() + arg2.intValue());
		} else {
			setValue(arg1.doubleValue() + arg2.doubleValue());
		}
	}

	/**
	 * Modifies the current value by subtracting it by decValue
	 * 
	 * @param decValue
	 *            argument
	 */
	public void subtract(Object decValue) {
		Number arg1 = checkAndSetArgument(value);
		Number arg2 = checkAndSetArgument(decValue);
		if (areBothInteger(arg1, arg2)) {
			setValue(arg1.intValue() - arg2.intValue());
		} else {
			setValue(arg1.doubleValue() - arg2.doubleValue());
		}

	}

	/**
	 * Modifies the current value by multiplying it with mulValue
	 * 
	 * @param mulValue
	 *            argument
	 */
	public void multiply(Object mulValue) {
		Number arg1 = checkAndSetArgument(value);
		Number arg2 = checkAndSetArgument(mulValue);
		if (areBothInteger(arg1, arg2)) {
			setValue(arg1.intValue() * arg2.intValue());
		} else {
			setValue(arg1.doubleValue() * arg2.doubleValue());
		}
	}

	/**
	 * Modifies the current value by dividing it with divValue
	 * 
	 * @param divValue
	 *            argument
	 */
	public void divide(Object divValue) {
		Number arg1 = checkAndSetArgument(value);
		Number arg2 = checkAndSetArgument(divValue);
		if (areBothInteger(arg1, arg2)) {
			setValue(arg1.intValue() / arg2.intValue());
		} else {
			setValue(arg1.doubleValue() / arg2.doubleValue());
		}
	}

	/**
	 * Method which doesn't perform any change in the stored data. It perform
	 * numerical comparison between currently stored value in ValueWrapper and given
	 * argument. If both are null we treat them as equal
	 * 
	 * @param withValue
	 *            argument
	 * @return 0-if equal, integer<0 if value smaller than argument, integer>0 if
	 *         value larger than argument
	 */
	public int numCompare(Object withValue) {
		if (value == null && withValue == null)
			return 0;
		else {
			Double d1 = checkAndSetArgument(value).doubleValue();
			Double d2 = checkAndSetArgument(withValue).doubleValue();
			return d1.compareTo(d2);
		}
	}

	/**
	 * Method used to check the Object value if it is supported for the rest of the
	 * process. If it is indeed null||String||Double||Integer returns the
	 * appropriate instance of Number.
	 * 
	 * @param value
	 *            Object being checked
	 * @return Double || Integer
	 * @throws IllegalArgumentException
	 *             if the value is not null||String||Double||Integer
	 */
	private Number checkAndSetArgument(Object value) {
		if (value == null) {
			return 0;
		} else if (value instanceof String) {
			if (checkIfStringIsNumber(value) instanceof Integer) {
				return Integer.valueOf((String) value);
			} else {
				return Double.valueOf((String) value);
			}
		} else if (value instanceof Integer || value instanceof Double) {
			return (Number) value;
		} else {
			throw new IllegalArgumentException("Argument of the function must be null||String||Integer||Double");
		}
	}

	/**
	 * Method that checks if the inputed Object is a string. Tries to returns a
	 * Double if the string contains . or E, otherwise tries to return an Integer.
	 * 
	 * @param value2
	 *            Object which is an instance of String
	 * @return Double or Integer
	 * @throws IllegalArgumentException
	 *             if the string can't be parsed
	 */
	private Number checkIfStringIsNumber(Object value2) {
		try {
			String input = (String) value2;
			if (input.contains(".") || input.contains("E")) {
				return Double.parseDouble(input);
			} else {
				return Integer.parseInt(input);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("String inputed is not a number");
		}
	}

	/**
	 * Method which checks if the provided arguments are both an instance of Integer
	 * 
	 * @param arg1
	 *            Number
	 * @param arg2
	 *            Number
	 * @return true if both are Integer otherwise false
	 */
	private boolean areBothInteger(Number arg1, Number arg2) {
		return (arg1 instanceof Integer && arg2 instanceof Integer);
	}
}
