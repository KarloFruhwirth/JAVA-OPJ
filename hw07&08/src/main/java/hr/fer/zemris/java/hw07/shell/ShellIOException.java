package hr.fer.zemris.java.hw07.shell;

public class ShellIOException extends RuntimeException {

	/**
	 * Default version serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public ShellIOException() {
	}

	/**
	 * Exception that sends message
	 * 
	 * @param message
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
