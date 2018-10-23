package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Class implementing {@link CalcModel}. Provides the methods used for the
 * functionality of the {@link Calculator}
 * 
 * @author KarloFrühwirth
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * User input, default set to null
	 */
	private String userInput = null;
	/**
	 * active operand,default set to Nan
	 */
	private double activeOperand = Double.NaN;
	/**
	 * Pending DoubleBinaryOperator
	 */
	private DoubleBinaryOperator pendingOperation = null;
	/**
	 * List of {@link CalcValueListener}s
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	/**
	 * Adds CalcValueListener to the list of CalcValueListeners
	 */
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	/**
	 * Removes CalcValueListener from the list of CalcValueListeners
	 */
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	/**
	 * Returns the double value of the userInput<br>
	 * If userInput is null returns 0.
	 */
	public double getValue() {
		if (userInput == null)
			return 0.0f;
		else {
			return Double.parseDouble(userInput);
		}
	}

	/**
	 * Sets the value of the userInput to value
	 * 
	 * @param value
	 *            double value to which userInput is set
	 * @throws IllegalArgumentException
	 *             if value is infinity or NaN
	 */
	public void setValue(double value) {
		if (Double.isInfinite(value) || Double.isNaN(value))
			throw new IllegalArgumentException("Value is infinity or NaN");
		userInput = Double.toString(value);
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Resets the userInput
	 */
	public void clear() {
		this.userInput = null;
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Resets userInput, activeOperand, pendingOperation
	 */
	public void clearAll() {
		clear();
		clearActiveOperand();
		this.pendingOperation = null;
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Reverses the sign of the value
	 */
	public void swapSign() {
		if (userInput != null) {
			if (userInput.charAt(0) == '-') {
				userInput = userInput.substring(1, userInput.length());
			} else {
				StringBuilder sb = new StringBuilder(userInput);
				sb.insert(0, '-');
				userInput = sb.toString();
			}
		}
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Inserts "." to the userInput
	 */
	public void insertDecimalPoint() {
		StringBuilder sb;
		if (userInput == null) {
			sb = new StringBuilder("0");
			sb.append(".");
			userInput = sb.toString();
		} else {
			sb = new StringBuilder(userInput);
			if (!userInput.contains(".")) {
				sb.append(".");
				userInput = sb.toString();
			}
		}
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Sets the value of the userInput to value
	 * 
	 * @param digit
	 *            integer value of a digit which is appended to userInput
	 */
	public void insertDigit(int digit) {
		StringBuilder sb;
		if (userInput == null) {
			sb = new StringBuilder();
			sb.append(Integer.toString(digit));
			userInput = sb.toString();
		} else {
			sb = new StringBuilder(userInput);
			if (!userInput.equals("0")) {
				sb.append(Integer.toString(digit));
				if (Double.parseDouble(sb.toString()) < Double.MAX_VALUE) {
					userInput = sb.toString();
				}
			} else {
				if (digit != 0) {
					userInput = Integer.toString(digit);
				}
			}
		}
		listeners.forEach(e -> e.valueChanged(this));
	}

	/**
	 * Returns true if activeOperand is set
	 * 
	 */
	public boolean isActiveOperandSet() {
		if (Double.isNaN(this.activeOperand))
			return false;
		return true;
	}

	/**
	 * Returns the activeOperand if it is set
	 * 
	 * @throws IllegalStateException
	 *             if activeOperand is undefined
	 */
	public double getActiveOperand() {
		if (!isActiveOperandSet())
			throw new IllegalStateException();
		return this.activeOperand;
	}

	/**
	 * Sets the value of the activeOperand
	 * 
	 * @param activeOperand
	 *            activeOperand
	 */
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	/**
	 * Resets the activeOperand
	 */
	public void clearActiveOperand() {
		this.activeOperand = Double.NaN;
	}

	/**
	 * Returns the pendingOperation
	 */
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	/**
	 * Sets the pending DoubleBinaryOperator
	 * 
	 * @param op
	 *            DoubleBinaryOperator pending for calculating
	 */
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		if (userInput == null)
			return "0";
		else {
			return userInput;
		}
	}
}
