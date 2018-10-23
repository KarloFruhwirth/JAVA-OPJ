package hr.fer.zemris.java.hw05.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that presents us with different strategies for
 * {@link IComparisonOperator} interface
 * 
 * @author KarloFrühwirth
 *
 */
public class ComparisonOperators {
	/**
	 * Strategy implementation that returns true if String value1 is < than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> {
		if (value1.compareTo(value2) < 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is <= than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		if (value1.compareTo(value2) <= 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is > than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		if (value1.compareTo(value2) > 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is >= than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		if (value1.compareTo(value2) >= 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is = than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		if (value1.compareTo(value2) == 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is != than String
	 * value2 Otherwise it returns false
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		if (value1.compareTo(value2) != 0) {
			return true;
		}
		return false;
	};
	/**
	 * Strategy implementation that returns true if String value1 is matched by
	 * regex of String value2 Otherwise it returns false
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		int numberOfWildcard = countWildcard(value2);
		if (numberOfWildcard > 1)
			throw new IllegalArgumentException("Wildcard can occure only zero or once in a string");
		else if (numberOfWildcard == 0) {
			Pattern pattern = Pattern.compile(value2);
			Matcher matcher = pattern.matcher(value1);
			if (matcher.find()) {
				return true;
			}
			return false;
		} else {
			if (value2.substring(0, 1).equals("*") && value2.length() == 1) {
				String pattern1 = ".*";
				Pattern pattern = Pattern.compile(pattern1);
				Matcher matcher = pattern.matcher(value1);
				if (matcher.find()) {
					return true;
				}
				return false;
			} else if (value2.substring(0, 1).equals("*")) {
				String pattern1 = ".*(" + value2.substring(1, value2.length()) + ")$";
				Pattern pattern = Pattern.compile(pattern1);
				Matcher matcher = pattern.matcher(value1);
				if (matcher.find()) {
					return true;
				}
				return false;
			} else if (value2.substring(value2.length() - 1, value2.length()).equals("*")) {
				String pattern1 = "^(" + value2.substring(0, value2.length() - 1) + ").*";
				Pattern pattern = Pattern.compile(pattern1);
				Matcher matcher = pattern.matcher(value1);
				if (matcher.find()) {
					return true;
				}
				return false;
			} else {
				String[] array = value2.split("\\*");
				String pattern1 = "^(" + array[0] + ")*(" + array[1] + ")$";
				Pattern pattern = Pattern.compile(pattern1);
				Matcher matcher = pattern.matcher(value1);
				if (matcher.find()) {
					return true;
				}
				return false;
			}
		}

	};

	/**
	 * Counts the number of * characters in a string
	 * 
	 * @param string
	 *            String being checked for wildcard
	 * @return number of occurrences
	 */
	private static int countWildcard(String string) {
		int i = 0;
		char[] array = string.toCharArray();
		for (char c : array) {
			if (c == '*')
				i++;
		}
		return i;
	}
}
