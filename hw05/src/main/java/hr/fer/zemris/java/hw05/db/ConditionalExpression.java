package hr.fer.zemris.java.hw05.db;

/**
 * Class used in the {@link QueryParser} for creating ConditionalExpressions
 * which are then the base for this solution
 * 
 * @author KarloFrühwirth
 */
public class ConditionalExpression {
	/**
	 * Reference of a IFieldValueGetter
	 */
	private IFieldValueGetter strategy;
	/**
	 * String literal
	 */
	private String string;
	/**
	 * Reference to IComparisonOperator strategy
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor for the ConditionalExpression
	 * 
	 * @param strategy
	 *            {@link IFieldValueGetter} strategy
	 * @param string
	 * @param comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter strategy, String string, IComparisonOperator comparisonOperator) {
		this.strategy = strategy;
		this.string = string;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for IFieldValueGetter strategy
	 * 
	 * @return strategy
	 */
	public IFieldValueGetter getFieldValue() {
		return strategy;
	}

	/**
	 * Setter for IFieldValueGetter strategy
	 * 
	 * @param strategy
	 */
	public void setFieldValue(IFieldValueGetter strategy) {
		this.strategy = strategy;
	}

	/**
	 * Getter for literal string
	 * 
	 * @return strategy
	 */
	public String getString() {
		return string;
	}

	/**
	 * Setter for literal string
	 * 
	 * @param strategy
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * Getter for IComparisonOperator comparisonOperator
	 * 
	 * @return strategy
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Setter for IComparisonOperator comparisonOperator
	 * 
	 * @param strategy
	 */
	public void setComparisonOperator(IComparisonOperator comparisonStrategy) {
		this.comparisonOperator = comparisonStrategy;
	}

}
