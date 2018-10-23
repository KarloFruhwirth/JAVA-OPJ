package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;

/**
 * Listener for object "modifications"
 * 
 * @author KarloFrühwirth
 *
 */
public interface DrawingModelListener {
	/**
	 * Informs listeners that new object is added
	 * 
	 * @param source
	 *            DrawingModel
	 * @param index0
	 *            index0
	 * @param index1
	 *            index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Informs listeners that object is removed
	 * 
	 * @param source
	 *            DrawingModel
	 * @param index0
	 *            index0
	 * @param index1
	 *            index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Informs listeners that object is changed
	 * 
	 * @param source
	 *            DrawingModel
	 * @param index0
	 *            index0
	 * @param index1
	 *            index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
