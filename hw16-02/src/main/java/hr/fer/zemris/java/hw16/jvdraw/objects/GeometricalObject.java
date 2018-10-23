package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * Abstract class representing GeometricalObject
 * 
 * @author KarloFrühwirth
 *
 */
public abstract class GeometricalObject {
	/**
	 * List of GeometricalObjectListener
	 */
	public List<GeometricalObjectListener> list = new ArrayList<>();

	/**
	 * Accepts GeometricalObjectVisitor
	 * 
	 * @param v
	 *            GeometricalObjectVisitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Adds GeometricalObjectListener
	 * 
	 * @param l
	 *            GeometricalObjectListener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		list.add(l);
	}

	/**
	 * Removes GeometricalObjectListener
	 * 
	 * @param l
	 *            GeometricalObjectListener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		list.remove(l);
	}

	/**
	 * Creates GeometricalObjectEditor
	 * 
	 * @return GeometricalObjectEditor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * @return Text
	 */
	public abstract String getText();

}