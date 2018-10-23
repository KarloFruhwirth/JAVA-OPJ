package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Exception used in {@link CalcModelImpl}
 * 
 * @author KarloFrühwirth
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty exception
	 */
	public CalcLayoutException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Exception that sends message
	 * 
	 * @param message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
