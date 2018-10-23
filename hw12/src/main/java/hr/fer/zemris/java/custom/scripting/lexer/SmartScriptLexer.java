package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * @author KarloFrühwirth Class which represents the lexer which is used for the
 *         SmartScriptParser It has 14 different SmartScriptTokenTypes and 2
 *         different SmartScriptLexerStates The lexer performs the process of
 *         converting a sequence of characters into a sequence of tokens which
 *         are then used in the parser Depending on the SmartScriptLexerState we
 *         have different sets of rules which are deffined in the task
 */
public class SmartScriptLexer {
	/**
	 * Input text in a char array
	 */
	private char[] data;
	/**
	 * current SmartScriptToken
	 */
	private SmartScriptToken token;
	/**
	 * Index of the first unprocessed char
	 */
	private int currentIndex;
	/**
	 * Current state of the lexer
	 */
	private SmartScriptLexerState state;

	/**
	 * Constructor that creates a new instance of a SmartScriptLexer it also checks
	 * if the input string is valid or not Depending on the first char of the string
	 * if valid it sets the SmartScriptLexerState
	 * 
	 * @param text
	 *            input
	 */
	public SmartScriptLexer(String text) {
		if (text == null)
			throw new IllegalArgumentException();
		this.data = text.toCharArray();
		currentIndex = 0;
		if (data.length > 0 && data[currentIndex] == '{') {
			setState(SmartScriptLexerState.TAG);
		} else {
			setState(SmartScriptLexerState.TEXT);
		}
	}

	/**
	 * Processes a sequence of characters and based by the predefined rules it turns
	 * them into tokens If the char is not supported by any rules in the tag
	 * resultes with a SmartScriptLexerException
	 * 
	 * @return token
	 */
	public SmartScriptToken nextToken() {
		if (data.length == currentIndex) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			currentIndex++;
			return token;
		} else if (currentIndex > data.length) {
			throw new SmartScriptLexerException("Current index is not in the array data");
		} else {
			if (state == SmartScriptLexerState.TEXT) {
				String word = word();
				token = new SmartScriptToken(SmartScriptTokenType.TEXT, word);
			} else {
				if (data[currentIndex] == '{' ) {
					currentIndex ++;
					if(data[currentIndex] == '$') {
						token = new SmartScriptToken(SmartScriptTokenType.TAGSTART, "{$");
						currentIndex ++;
					} else {
						throw new SmartScriptParserException("Invalid tag start");
					}
				} else if (data[currentIndex] == '$') {
					currentIndex++;
					if(data[currentIndex] == '}') {
						token = new SmartScriptToken(SmartScriptTokenType.TAGEND, "$}");
						setState(SmartScriptLexerState.TEXT);
						currentIndex++;
					} else {
						throw new SmartScriptParserException("Invalid tag end");
					}
				} else if (data[currentIndex] == ' ' || data[currentIndex] == '\r'  || data[currentIndex] == '\n' || data[currentIndex] == '\t')  {
					String spaces = wightspaces();
					token = new SmartScriptToken(SmartScriptTokenType.SPACE, spaces);
				} else if (data[currentIndex] == 'F' || data[currentIndex] == 'f') {
					String tag = checkTag();
					if (tag.equalsIgnoreCase("FOR")) {
						currentIndex += 3;
						token = new SmartScriptToken(SmartScriptTokenType.FOR, tag);
					}
				} else if (data[currentIndex] == 'E' || data[currentIndex] == 'e') {
					String tag = checkTag();
					if (tag.equalsIgnoreCase("END")) {
						currentIndex += 3;
						token = new SmartScriptToken(SmartScriptTokenType.END, tag);
					}
				} else if (data[currentIndex] == '=' && data[currentIndex - 1] == '$') {
					currentIndex++;
					token = new SmartScriptToken(SmartScriptTokenType.EQUALS, "=");
				} else if (data[currentIndex] == '@') {
					currentIndex++;
					String function = function();
					token = new SmartScriptToken(SmartScriptTokenType.FUNCION, function);
				} else if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
						|| data[currentIndex] == '/' || data[currentIndex] == '^') {
					if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
						currentIndex++;
						String number = number();
						if (!isDouble(number)) {
							token = new SmartScriptToken(SmartScriptTokenType.INTEGER, -Integer.parseInt(number));
						} else {
							token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, -Double.parseDouble(number));
						}
					} else {
						token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, String.valueOf(data[currentIndex]));
						currentIndex++;
					}
				} else if (Character.isDigit(data[currentIndex])) {
					String number = number();
					if (!isDouble(number)) {
						token = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.parseInt(number));
					} else {
						token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.parseDouble(number));
					}
				} else if (Character.isLetter(data[currentIndex])) {
					String varName = variable();
					token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, varName);
				} else if (data[currentIndex] == '"') {
					currentIndex++;
					String special = quotationString();
					token = new SmartScriptToken(SmartScriptTokenType.STRING, special);

				} else {
					throw new SmartScriptLexerException("Symbole doesn have a token..." + data[currentIndex]);
				}
			}
		}
		return token;

	}

	/**
	 * Method that checks if the string that represents a number contains "."
	 * 
	 * @param special
	 *            String that is being checked
	 * @return true | false depending on the results of the check
	 */
	public boolean isDouble(String special) {
		char[] array;
		boolean point = false;

		array = special.toCharArray();
		for (char c : array) {
			if (c == '.') {
				point = true;
			}
		}
		return point;
	}

	/**
	 * Method that checks if the string represents a number
	 * 
	 * @param special
	 *            String that is being checked
	 * @return true | false depending on the results of the check
	 */
	public boolean isNumeric(String special) {
		char[] array;
		boolean point = true;

		array = special.toCharArray();
		for (char c : array) {
			if (!(Character.isDigit(c)) || (c == '.' && point == false)) {
				return false;
			}
			if (c == '.') {
				point = false;
			}
		}
		return true;
	}

	/**
	 * Method that returns the string within the " "
	 * 
	 * @return string
	 */
	public String quotationString() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (data[currentIndex] == '\\') {
				currentIndex++;
				escapecaracter(sb);
				currentIndex++;

			} else {
				if (data[currentIndex] == '"') {
					currentIndex++;
					break;
				}
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		String special = sb.toString();

		return special;
	}

	/**
	 * Method that returns the string representation of the variable
	 * 
	 * @return string
	 */
	public String variable() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (data[currentIndex] == ' ')
				break;
			else if (data[currentIndex] != '_' && !(Character.isLetter(data[currentIndex]))
					&& !(Character.isDigit(data[currentIndex])))
				break;
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		String name = sb.toString();

		return name;
	}

	/**
	 * Method that returns the string representation of the number
	 * 
	 * @return string
	 */
	public String number() {
		StringBuilder sb = new StringBuilder();
		boolean point = true;

		while (true) {
			if (!(Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')
					|| (data[currentIndex] == '.' && point == false))
				break;
			else if (data[currentIndex] == '.') {
				point = false;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		String text = sb.toString();

		return text;
	}

	/**
	 * Method that returns the string representation of the function
	 * 
	 * @return string
	 */
	public String function() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (!(Character.isLetter(data[currentIndex]))) {
				throw new SmartScriptLexerException("Function must start with a letter");
			} else {
				sb.append(data[currentIndex]);
				currentIndex++;
				if (data[currentIndex] == ' ' || data[currentIndex] == '\r'  || data[currentIndex] == '\n' || data[currentIndex] == '\t') {
					break;
				} else if (!(Character.isLetter(data[currentIndex])) && !(Character.isDigit(data[currentIndex]))) {
					throw new SmartScriptLexerException(
							"Function must start with a letter and contain only letters or numbers");
				}
			}
		}
		String function = sb.toString();

		return function;
	}

	/**
	 * Method that returns end or for tag
	 * 
	 * @return tag
	 */
	public String checkTag() {
		StringBuilder sb = new StringBuilder();

		for (int i = currentIndex; i < currentIndex + 3; i++) {
			sb.append(data[i]);
		}
		String tag = sb.toString();

		return tag;
	}

	/**
	 * Method that is used for creating tokens of wightspaces
	 * 
	 * @return
	 */
	public String wightspaces() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (data[currentIndex] != ' ' && data[currentIndex] != '\r'  && data[currentIndex] != '\n' && data[currentIndex] != '\t' )
				break;
			sb.append(data[currentIndex]);
			currentIndex++;
			if (data.length <= currentIndex)
				break;
		}
		String spaces = sb.toString();

		return spaces;

	}

	/**
	 * Method used when lexer is in word state and is used to get the string before
	 * the start of a tag {
	 * 
	 * @return wordTag
	 */
	public String word() {
		StringBuilder sb = new StringBuilder();

		while (true) {
			if (data[currentIndex] == '\\') {
				currentIndex++;
				escapecaracter(sb);
				currentIndex++;

			} else {
				if (data[currentIndex] == '{') {
					setState(SmartScriptLexerState.TAG);
					break;
				}
				sb.append(data[currentIndex]);
				currentIndex++;
				if (data.length <= currentIndex)
					break;
			}
		}
		String value = sb.toString();

		return value;
	}

	private void escapecaracter(StringBuilder sb) {
		if (data.length == currentIndex) {
			throw new SmartScriptLexerException("Index out of bounds");
		}
		switch (data[currentIndex]) {
		case ('\\'):
			sb.append(data[currentIndex]);
			break;
		case ('{'):
			sb.append(data[currentIndex]);
			break;
		case ('r'):
			sb.append('\r');
			break;
		case ('t'):
			sb.append('\t');
			break;
		case ('n'):
			sb.append('\n');
			break;
		case ('"'):
			sb.append("\\\"");
			break;
		default:
			throw new SmartScriptLexerException();
		}

	}

	/**
	 * Sets lexer state to the given state
	 * 
	 * @param state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null)
			throw new IllegalArgumentException("SmartScriptLexerState mustn't be null");
		this.state = state;
	}

	/**
	 * Getter for the token
	 * 
	 * @return token
	 */
	public SmartScriptToken getToken() {
		return token;
	}
}
