package hr.fer.zemris.java.hw03.prob1;

/**
 * @author KarloFrühwirth
 *Exception that inherits RuntimeException and is an implementation of a custom exception
 */
public class LexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public LexerException() {
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * Exception that sends message
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}

}
