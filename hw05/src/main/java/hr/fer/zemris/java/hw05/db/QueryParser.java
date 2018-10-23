package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for the input string. Depending on {@link QueryToken} provided by
 * {@link QueryLexer} goes through the input string and creates
 * {@link ConditionalExpression}s if it can.
 * 
 * @author KarloFrühwirth
 *
 */
public class QueryParser {
	/**
	 * QueryLexer lexer used for parser
	 */
	private QueryLexer queryLexer;
	/**
	 * QueryToken token
	 */
	private QueryToken token;
	/**
	 * boolean used to check if more than one word query is inputed
	 */
	private boolean queryCorrect = true;
	/**
	 * List of ConditionalExpression
	 */
	private List<ConditionalExpression> list = new ArrayList<ConditionalExpression>();

	/**
	 * Constructor for QueryParser
	 * 
	 * @param queryString
	 *            input string
	 */
	public QueryParser(String queryString) {
		if (queryString == null)
			throw new NullPointerException();
		queryLexer = new QueryLexer(queryString);
		parse(queryLexer);
	}

	/**
	 * Parses the inputed string and returns a list of ConditionalExpression
	 * 
	 * @throws IllegalArgumentException
	 *             if an error occures
	 * @param queryLexer
	 *            lexer used for parser
	 * @return list of ConditionalExpression
	 */
	private List<ConditionalExpression> parse(QueryLexer queryLexer) {
		while (true) {
			token = queryLexer.nextToken();
			if (token.getType() == QueryTokenType.EOL)
				break;
			else if (token.getType() == QueryTokenType.QUERY) {
				if (queryCorrect == true) {
					queryCorrect = false;
				} else {
					throw new IllegalArgumentException("Too many query inputs in one query");
				}
			} else if (token.getType() == QueryTokenType.SPACE) {
				continue;
			} else if (token.getType() == QueryTokenType.ATRIBUTE) {
				String atribute = token.getValue();
				if (!(atribute.equals("jmbag"))) {
					queryLexer.setState(QueryLexerState.DEFAULT);
				}
				token = queryLexer.nextToken();
				if (token.getType() == QueryTokenType.SPACE) {
					token = queryLexer.nextToken();
				}
				if (token.getType() != QueryTokenType.OPERATION) {
					throw new IllegalArgumentException("After a students atribute must come an operation!");
				}
				String operation = token.getValue();
				if (!(operation.equals("="))) {
					queryLexer.setState(QueryLexerState.DEFAULT);
				}
				token = queryLexer.nextToken();
				if (token.getType() == QueryTokenType.SPACE) {
					token = queryLexer.nextToken();
				}
				if (token.getType() != QueryTokenType.STRING) {
					throw new IllegalArgumentException("After an operation must come a string");
				}
				String string = token.getValue();
				ConditionalExpression expresion = setConditionalExpression(atribute, operation, string);
				list.add(expresion);
			} else if (token.getType() == QueryTokenType.AND) {
				token = queryLexer.nextToken();
				if (token.getType() == QueryTokenType.EOL) {
					throw new IllegalArgumentException("After and must come another conditional expresion");
				}
				queryLexer.setState(QueryLexerState.DEFAULT);
			}
		}
		return null;
	}

	/**
	 * Used to create a ConditionalExpression based on the string values of tokens
	 * 
	 * @param atribute
	 *            IFieldValueGetter string value
	 * @param operation
	 *            IComparisonOperator string value
	 * @param string
	 *            string in literals
	 * @return ConditionalExpression
	 */
	private ConditionalExpression setConditionalExpression(String atribute, String operation, String string) {
		IFieldValueGetter fieldValue;
		switch (atribute) {
		case ("lastName"):
			fieldValue = FieldValueGetters.LAST_NAME;
			break;
		case ("firstName"):
			fieldValue = FieldValueGetters.FIRST_NAME;
			break;
		case ("jmbag"):
			fieldValue = FieldValueGetters.JMBAG;
			break;
		default:
			throw new IllegalAccessError();
		}
		IComparisonOperator operator;
		switch (operation) {
		case ("<"):
			operator = ComparisonOperators.LESS;
			break;
		case ("<="):
			operator = ComparisonOperators.LESS_OR_EQUALS;
			break;
		case (">"):
			operator = ComparisonOperators.GREATER;
			break;
		case (">="):
			operator = ComparisonOperators.GREATER_OR_EQUALS;
			break;
		case ("LIKE"):
			operator = ComparisonOperators.LIKE;
			break;
		case ("="):
			operator = ComparisonOperators.EQUALS;
			break;
		case ("!="):
			operator = ComparisonOperators.NOT_EQUALS;
			break;
		default:
			throw new IllegalAccessError();
		}
		return new ConditionalExpression(fieldValue, string, operator);
	}

	/**
	 * Checks if the lexer is in DIRECT state
	 * 
	 * @return true if lexer is in DIRECT state else return false
	 */
	public boolean isDirectQuery() {
		if (queryLexer.getState() == QueryLexerState.DIRECT) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the jmbag of DirectQuery
	 * 
	 * @throws IllegalArgumentException
	 *             if condition doesnt have a jmbag
	 * @return jmbag of DirectQuery
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return this.getQuery().get(0).getString();
		} else {
			throw new IllegalArgumentException("Condition doesnt have a JMBAG");
		}

	}

	/**
	 * Getter for ConditionalExpression list
	 * 
	 * @return list of ConditionalExpression
	 */
	public List<ConditionalExpression> getQuery() {
		return list;
	}
}
