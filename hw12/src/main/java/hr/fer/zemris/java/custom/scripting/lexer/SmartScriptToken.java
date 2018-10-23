package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * @author KarloFrühwirth Class that creates a SmartScriptToken
 */
public class SmartScriptToken {
	/**
	 * type of token
	 */
	private SmartScriptTokenType type;
	/**
	 * value of token
	 */
	private Object value;

	/**
	 * Constructor that creates an instance of SmartScriptToken and sets the type
	 * and value to the given params
	 * 
	 * @param type
	 *            SmartScriptTokenType
	 * @param value
	 *            Object
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		if (type == null)
			throw new IllegalArgumentException("SmartScriptTokenType mustn't be null");
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for the value
	 * 
	 * @return
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Getter for the token type
	 * 
	 * @return
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}

}
