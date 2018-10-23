package hr.fer.zemris.java.hw05.db;

/**
 * Used to lexer the inputed string letter by letter and depending on the rules
 * it returns different {@link QueryToken}s which are then used in
 * {@link QueryLexer} It provides us with appropriate getters and setters and
 * the method nextToken.
 * 
 * @author KarloFrühwirth
 *
 */
public class QueryLexer {
	/**
	 * Character array that is used to store the input string
	 */
	private char[] input;
	/**
	 * QueryLexerState
	 */
	private QueryLexerState state;
	/**
	 * Indexer used to go through the input array
	 */
	int currentIndex;
	/**
	 * QueryToken
	 */
	private QueryToken token;

	/**
	 * Constructor for QueryLexer
	 * 
	 * @param queryString
	 */
	public QueryLexer(String queryString) {
		if (queryString.equals(""))
			throw new IllegalArgumentException("Empty imput... try again");
		input = queryString.toCharArray();
		setState(QueryLexerState.DIRECT);
	}

	/**
	 * Used to lexer the inputed string letter by letter and depending on the rules
	 * it returns different {@link QueryToken}s which are then used in
	 * {@link QueryLexer}
	 * 
	 * @throws QueryLexerException
	 *             if an error occurs
	 * @return
	 */
	public QueryToken nextToken() {
		if (input.length == currentIndex) {
			token = new QueryToken(QueryTokenType.EOL, null);
			currentIndex++;
			return token;
		}
		if (currentIndex > input.length) {
			throw new QueryLexerException("Current index is not in the array data");
		} else {
			if (input[currentIndex] == ' ') {
				String spaces = wightspaces();
				token = new QueryToken(QueryTokenType.SPACE, spaces);
			} else if (input[currentIndex] == 'q') {
				String s = checkWord();
				if (s.equals("query")) {
					token = new QueryToken(QueryTokenType.QUERY, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == 'f') {
				String s = checkWord();
				if (s.equals("firstName")) {
					token = new QueryToken(QueryTokenType.ATRIBUTE, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == 'l') {
				String s = checkWord();
				if (s.equals("lastName")) {
					token = new QueryToken(QueryTokenType.ATRIBUTE, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == 'j') {
				String s = checkWord();
				if (s.equals("jmbag")) {
					token = new QueryToken(QueryTokenType.ATRIBUTE, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == 'a' || input[currentIndex] == 'A') {
				String s = checkWord().toLowerCase();
				if (s.equals("and")) {
					token = new QueryToken(QueryTokenType.AND, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == '"') {
				String s = getString();
				token = new QueryToken(QueryTokenType.STRING, s);
			} else if ((input[currentIndex] == 'L')) {
				String s = checkWord();
				if (s.equals("LIKE")) {
					token = new QueryToken(QueryTokenType.OPERATION, s);
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == '=') {
				token = new QueryToken(QueryTokenType.OPERATION, "=");
				currentIndex++;
			} else if (input[currentIndex] == '!') {
				String s = checkOperation();
				if (s.equals("!=")) {
					token = new QueryToken(QueryTokenType.OPERATION, "!=");
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == '<') {
				String s = checkOperation();
				if (s.equals("<")) {
					token = new QueryToken(QueryTokenType.OPERATION, "<");
				} else if (s.equals("<=")) {
					token = new QueryToken(QueryTokenType.OPERATION, "<=");
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			} else if (input[currentIndex] == '>') {
				String s = checkOperation();
				if (s.equals(">")) {
					token = new QueryToken(QueryTokenType.OPERATION, ">");
				} else if (s.equals(">=")) {
					token = new QueryToken(QueryTokenType.OPERATION, ">=");
				} else {
					throw new QueryLexerException("This word is not supported " + s);
				}
			}
		}
		return token;
	}

	/**
	 * Used to return a string that represents an operation
	 * 
	 * @return string
	 */
	private String checkOperation() {
		StringBuilder sb = new StringBuilder();
		while (input[currentIndex] != ' ') {
			if (input[currentIndex] != '<' && input[currentIndex] != '=' && input[currentIndex] != '>'
					&& input[currentIndex] != '<') {
				break;
			}
			sb.append(input[currentIndex]);
			currentIndex++;
		}
		String s = sb.toString();

		return s;
	}

	/**
	 * Used to return a string that represents an string in literals
	 * 
	 * @return string
	 */
	private String getString() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while (input[currentIndex] != '"') {
			sb.append(input[currentIndex]);
			currentIndex++;
		}
		currentIndex++;
		String s = sb.toString();

		return s;
	}

	/**
	 * Used to return a string that represents an attribute, AND or LIKE
	 * 
	 * @return string
	 */
	private String checkWord() {
		StringBuilder sb = new StringBuilder();
		while (input[currentIndex] != ' ') {
			if (input[currentIndex] == '<' || input[currentIndex] == '=' || input[currentIndex] == '>'
					|| input[currentIndex] == '<') {
				break;
			}
			sb.append(input[currentIndex]);
			currentIndex++;
		}
		String s = sb.toString();

		return s;
	}

	/**
	 * Method used to skip spaces in a string
	 * 
	 * @return
	 */
	private String wightspaces() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (input[currentIndex] != ' ')
				break;
			sb.append(input[currentIndex]);
			currentIndex++;
		}
		String spaces = sb.toString();

		return spaces;
	}

	/**
	 * Setter for QueryLexerState
	 * 
	 * @param state
	 */
	public void setState(QueryLexerState state) {
		this.state = state;
	}

	/**
	 * Getter for QueryLexerState
	 * 
	 * @return state
	 */
	public QueryLexerState getState() {
		return state;
	}

}
