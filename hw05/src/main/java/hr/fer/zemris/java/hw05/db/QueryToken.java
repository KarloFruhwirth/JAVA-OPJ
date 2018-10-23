package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents token used in {@link QueryLexer}
 * 
 * @author KarloFrühwirth
 *
 */
public class QueryToken {
	/**
	 * QueryTokenType which a token is
	 */
	private QueryTokenType type;
	/**
	 * String value of the token
	 */
	private String value;

	/**
	 * Constructor for the QueryToken
	 * 
	 * @param type
	 *            QueryTokenType
	 * @param value
	 *            String
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for the QueryTokenType
	 * 
	 * @return
	 */
	public QueryTokenType getType() {
		return type;
	}

	/**
	 * Getter for the String
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

}
