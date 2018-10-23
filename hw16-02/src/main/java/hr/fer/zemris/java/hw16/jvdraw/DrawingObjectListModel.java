package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Wrapper used to display objects in {@link JVDraw} JList
 * 
 * @author KarloFrühwirth
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * DrawingModel
	 */
	private DrawingModel dm;

	/**
	 * Constructor
	 * 
	 * @param dm
	 *            DrawingModel
	 */
	public DrawingObjectListModel(DrawingModel dm) {
		this.dm = dm;
		dm.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int arg0) {
		return dm.getObject(arg0);
	}

	@Override
	public int getSize() {
		return dm.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
