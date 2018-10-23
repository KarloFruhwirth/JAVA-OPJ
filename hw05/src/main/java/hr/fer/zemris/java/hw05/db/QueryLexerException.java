package hr.fer.zemris.java.hw05.db;

/**
 * Exception used in QueryLexer
 * 
 * @author KarloFrühwirth
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public QueryLexerException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Exception that sends message
	 * 
	 * @param message
	 */
	public QueryLexerException(String message) {
		super(message);
	}

}
