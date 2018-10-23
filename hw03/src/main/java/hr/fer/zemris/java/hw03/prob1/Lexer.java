package hr.fer.zemris.java.hw03.prob1;

/**
 * @author KarloFrühwirth
 *
 */
public class Lexer {
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex;// indeks prvog neobrađenog znaka
	private LexerState state;

	/**
	 * Constructor for Lexer which tokenises the inputed text if the text is null it
	 * throws IllegalArgumentException Also the starting state for each lexer is
	 * BASIC
	 * 
	 * @param text
	 *            input text which is tokenised
	 */
	public Lexer(String text) {
		if (text == null)
			throw new IllegalArgumentException();
		text = text.trim();
		text = text.replaceAll("\\s+", " ");
		int length = text.length();
		char[] ulaz = text.toCharArray();
		data = new char[length];
		//Arrays.copyof...
		for (int i = 0; i < length; i++) {
			data[i] = ulaz[i];
		}
		this.currentIndex = 0;
		setState(LexerState.BASIC);
	}

	/**
	 * Generates and returns the next token if an error occurs it throws
	 * LexerException
	 * 
	 * @return the next token
	 */
	public Token nextToken() {
		if (data.length == currentIndex) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return token;
		} else if (currentIndex > data.length) {
			throw new LexerException();
		} else {
			if (state == LexerState.BASIC) {
				if (data[currentIndex] == '\\' || Character.isLetter(data[currentIndex])) {
					String word = word();
					token = new Token(TokenType.WORD, word);
				} else if (Character.isDigit(data[currentIndex])) {
					long number = number();
					token = new Token(TokenType.NUMBER, number);
				} else {
					Character symbol = symbol();
					token = new Token(TokenType.SYMBOL, symbol);
				}
			} else {
				if (data[currentIndex] == '\\' || Character.isLetter(data[currentIndex])
						|| Character.isDigit(data[currentIndex])) {
					String word = wordExtended();
					token = new Token(TokenType.WORD, word);
				} else {
					Character symbol = symbol();
					token = new Token(TokenType.SYMBOL, symbol);
				}
			}
		}
		return token;
	}

	private String wordExtended() {
		String value = "";
		while (true) {
			if (data[currentIndex] == ' ') {
				currentIndex++;
				break;
			} else if (data[currentIndex] == '#'){
				break;
			} else {
				value += data[currentIndex];
				currentIndex++;
				if (data.length <= currentIndex)
					break;
			}
		}
		return value;
	}

	private Character symbol() {
		Character value = data[currentIndex];
		currentIndex++;
		if (data.length > currentIndex) {
			while (data[currentIndex] == ' ') {
				currentIndex++;
				if (data.length <= currentIndex)
					break;
			}
		}
		if (state == LexerState.BASIC && value == '#') {
			setState(LexerState.EXTENDED);
		} else if (state == LexerState.EXTENDED && value == '#') {
			setState(LexerState.BASIC);
		}
		return value;
	}

	private long number() {
		String value = "";
		while (true) {
			if (data[currentIndex] == ' ') {
				currentIndex++;
				break;
			} else if (Character.isDigit(data[currentIndex])) {
				value += data[currentIndex];
				currentIndex++;
				if (data.length <= currentIndex)
					break;
			} else {
				break;
			}
		}
		try {
			long number = Long.parseLong(value);
			return number;
		} catch (NumberFormatException e) {
			throw new LexerException();
		}
	}

	private String word() {
		String value = "";
		while (true) {
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (data.length == currentIndex) {
					throw new LexerException();
				} else if (data[currentIndex] == '\\') {
					value += '\\';
					currentIndex++;
				} else if (Character.isDigit(data[currentIndex])) {
					value += data[currentIndex];
					currentIndex++;
				} else {
					throw new LexerException();
				}
				if (data.length <= currentIndex)
					break;
			} else if (data[currentIndex] == ' ') {
				currentIndex++;
				break;
			} else if (Character.isDigit(data[currentIndex])) {
				break;
			} else if (Character.isLetter(data[currentIndex])) {
				value += data[currentIndex];
				currentIndex++;
				if (data.length <= currentIndex)
					break;
			}
		}
		return value;
	}

	/**
	 * Method returns the last generated token it can be called multiple times and
	 * it has no effect on the returned token
	 * 
	 * @return token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method sets the state for the lexer in which it works it works in the current
	 * state until the first occurrence of the symbol # when the lexer changes its
	 * state
	 * 
	 * @param state
	 *            to which the lexer is set
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Lexer state cant be null");
		}
		this.state = state;
	}

}
