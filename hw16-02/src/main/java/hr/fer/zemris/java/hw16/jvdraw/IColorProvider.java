package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Interface for color provider
 * 
 * @author KarloFrühwirth
 *
 */
public interface IColorProvider {
	/**
	 * Getter for Color
	 * 
	 * @return Color
	 */
	public Color getCurrentColor();

	/**
	 * Adds ColorChangeListener
	 * 
	 * @param l
	 *            ColorChangeListener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes ColorChangeListener
	 * 
	 * @param l
	 *            ColorChangeListener
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
