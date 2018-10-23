package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

/**
 * Listener for color changing
 * 
 * @author KarloFrühwirth
 *
 */
public interface ColorChangeListener {
	/**
	 * Informs listeners that new color is selected
	 * 
	 * @param source
	 *            IColorProvider
	 * @param oldColor
	 *            Color
	 * @param newColor
	 *            Color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
