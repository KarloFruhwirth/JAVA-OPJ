package hr.fer.zemris.java.hw03.prob1;

/**
 * @author KarloFrühwirth
 * Class that creates a Token
 */
public class Token {
	private TokenType type;
	private Object value;

	/**
	 * Constructor that creates an instance of Token 
	 * and sets the type and value to the given params
	 * @param type TokenType
	 * @param value Object
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for the value
	 * @return
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Getter for the token type
	 * @return
	 */
	public TokenType getType() {
		return this.type;
	}

}
