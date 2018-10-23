package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Model who's implementation provides us with objects.<br>
 * See {@link DocumentModel}
 * 
 * @author KarloFrühwirth
 *
 */
public interface DrawingModel {
	/**
	 * Returns size of DrawingModel
	 * 
	 * @return size
	 */
	public int getSize();

	/**
	 * Getter for GeometricalObject
	 * 
	 * @param index
	 *            Index
	 * @return GeometricalObject from list
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Remove GeometricalObject from list
	 * 
	 * @param object
	 *            GeometricalObject
	 */
	void remove(GeometricalObject object);

	/**
	 * Change order of GeometricalObjects in list
	 * 
	 * @param object
	 *            GeometricalObject
	 * @param offset
	 *            offset
	 */
	void changeOrder(GeometricalObject object, int offset);

	/**
	 * Adds GeometricalObject to list
	 * 
	 * @param object
	 *            GeometricalObject
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds DrawingModelListener
	 * 
	 * @param l
	 *            DrawingModelListener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes DrawingModelListener
	 * 
	 * @param l
	 *            DrawingModelListener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
