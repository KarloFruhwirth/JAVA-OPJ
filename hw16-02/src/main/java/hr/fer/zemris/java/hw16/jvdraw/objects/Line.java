package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.editor.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * Line used in {@link JVDraw}
 * 
 * @author KarloFrühwirth
 *
 */
public class Line extends GeometricalObject {

	/**
	 * x0
	 */
	private int startX;
	/**
	 * y0
	 */
	private int startY;
	/**
	 * x1
	 */
	private int endX;
	/**
	 * y1
	 */
	private int endY;
	/**
	 * Color
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            x0
	 * @param startY
	 *            y0
	 * @param endX
	 *            x1
	 * @param endY
	 *            y1
	 * @param r
	 *            R
	 * @param g
	 *            G
	 * @param b
	 *            B
	 */
	public Line(int startX, int startY, int endX, int endY, int r, int g, int b) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = new Color(r, g, b);
	}

	/**
	 * Constructor
	 * 
	 * @param startX
	 *            x0
	 * @param startY
	 *            y0
	 * @param endX
	 *            x1
	 * @param endY
	 *            y1
	 * @param color
	 *            Color
	 */
	public Line(int startX, int startY, int endX, int endY, Color color) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Line (" + startX + "," + startY + ")-(" + endX + "," + endY + ")";
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	// LINE 10 10 50 50 255 255 0
	@Override
	public String getText() {
		return String.format("LINE %d %d %d %d %d %d %d", startX, startY, endX, endY, color.getRed(), color.getGreen(),
				color.getBlue());
	}

}
