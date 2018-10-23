package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * Our canvas similar to glcanvas, used to display objects in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * DrawingModel
	 */
	private DrawingModel dm;
	/**
	 * Tool
	 */
	private Tool source;
	/**
	 * JVDraw
	 */
	private JVDraw jvd;

	/**
	 * Setter for source
	 * 
	 * @param source
	 *            Tool
	 */
	public void setSource(Tool source) {
		this.source = source;
	}

	/**
	 * Getter for source
	 * 
	 * @return source
	 */
	public Tool getSource() {
		return source;
	}

	/**
	 * Constructor
	 * 
	 * @param dm
	 *            DrawingModel
	 * @param jvDraw
	 *            JVDraw
	 */
	public JDrawingCanvas(DrawingModel dm, JVDraw jvDraw) {
		this.dm = dm;
		dm.addDrawingModelListener(this);
		setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
		this.jvd = jvDraw;
	}

	@Override
	protected void paintComponent(Graphics g) {
		GeometricalObjectVisitor v = new GeometricalObjectPainter((Graphics2D) g);
		for (int i = 0, limit = dm.getSize(); i < limit; i++) {
			dm.getObject(i).accept(v);
		}
		source.paint((Graphics2D) g);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		jvd.setModified(true);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		jvd.setModified(true);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		jvd.setModified(true);
	}

}
