package hr.fer.zemris.java.custom.collections;

/**
 * @author KarloFrühwirth
 * @version 1.0
 * 
 * Created exception for the purposes of this task
 */
public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public EmptyStackException() {
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * Exception that sends message
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
