package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * @author KarloFrühwirth
 * Exception that inherits RuntimeException and is an implementation of a custom exception
 */
public class SmartScriptLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public SmartScriptLexerException() {
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * Exception that sends message
	 * @param message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

}
