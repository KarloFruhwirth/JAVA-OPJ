package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Implementation of {@link DrawingModel} and {@link GeometricalObjectListener}
 * Essential for the {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class DocumentModel implements DrawingModel, GeometricalObjectListener {

	/**
	 * list of GeometricalObjects
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * list of DrawingModelListener
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		listeners.forEach(l -> l.objectsAdded(this, getSize() - 1, getSize() - 1));
		object.addGeometricalObjectListener(this);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		listeners.forEach(l -> l.objectsChanged(this, 0, 0));
	}

	@Override
	public void remove(GeometricalObject object) {
		objects.remove(object);
		listeners.forEach(l -> l.objectsRemoved(this, 0, getSize()));
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		int size = getSize();

		if (offset == 1) {
			if (index == 0)
				return;
			GeometricalObject pom = objects.get(index - 1);
			objects.set(index - 1, object);
			objects.set(index, pom);
			listeners.forEach(l -> l.objectsChanged(this, 0, getSize() - 1));
		}
		if (offset == -1) {
			if (index == size - 1)
				return;
			GeometricalObject pom = objects.get(index + 1);
			objects.set(index + 1, object);
			objects.set(index, pom);
			listeners.forEach(l -> l.objectsChanged(this, 0, getSize() - 1));
		}
	}

}
