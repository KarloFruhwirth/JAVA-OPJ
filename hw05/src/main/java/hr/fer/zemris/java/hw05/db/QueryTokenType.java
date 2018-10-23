package hr.fer.zemris.java.hw05.db;

/**
 * Enum that defines the types of {@link QueryToken} can be
 * 
 * @author KarloFrühwirth
 *
 */
public enum QueryTokenType {
	/**
	 * QueryTokenType if QueryToken is query
	 */
	QUERY,
	/**
	 * QueryTokenType if QueryToken is any number of spaces
	 */
	SPACE,
	/**
	 * QueryTokenType if QueryToken is one of the defined operations
	 */
	OPERATION,
	/**
	 * QueryTokenType if QueryToken is a string in literals
	 */
	STRING,
	/**
	 * QueryTokenType if QueryToken is and
	 */
	AND,
	/**
	 * QueryTokenType if QueryToken is end of line
	 */
	EOL,
	/**
	 * QueryTokenType if QueryToken is one of the defined atributes
	 */
	ATRIBUTE
}
