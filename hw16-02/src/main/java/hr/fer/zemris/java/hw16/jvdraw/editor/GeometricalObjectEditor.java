package hr.fer.zemris.java.hw16.jvdraw.editor;

import javax.swing.JPanel;

/**
 * Abstract class, provides us with methods used to edit object
 * 
 * @author KarloFrühwirth
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks if the entered parameters are valid
	 */
	public abstract void checkEditing();

	/**
	 * Changes the object based on the inputed data
	 */
	public abstract void acceptEditing();
}
