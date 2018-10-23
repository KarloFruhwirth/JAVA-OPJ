package hr.fer.zemris.java.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public SmartScriptParserException() {
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * Exception that sends a message to the user
	 * @param message describes the error that has occurred
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
