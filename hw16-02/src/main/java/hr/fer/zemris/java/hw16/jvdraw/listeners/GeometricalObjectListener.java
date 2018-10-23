package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Listener for object "modifications"
 * 
 * @author KarloFrühwirth
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Informs listeners that GeometricalObject is changed
	 * 
	 * @param o
	 *            GeometricalObject
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}