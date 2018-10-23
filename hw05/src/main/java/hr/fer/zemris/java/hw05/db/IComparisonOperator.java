package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides a single method satisfied Different strategies are
 * provided in {@link ComparisonOperators}
 * 
 * @author KarloFrühwirth
 *
 */
public interface IComparisonOperator {
	/**
	 * Method that depending on the strategy returns true or false
	 * 
	 * @param value1
	 *            String being compared
	 * @param value2
	 *            String compared to
	 * @return true or false
	 */
	public boolean satisfied(String value1, String value2);
}
